package com.alisha.server;

import com.alisha.server.commands.SaveCommand;
import com.alisha.server.utilities.FileManager;
import com.alisha.server.utilities.JsonParser;
import org.slf4j.Logger;
import com.alisha.common.commands.Command;
import com.alisha.common.commands.HelpCommand;
import com.alisha.common.data.Route;
import com.alisha.common.dto.CommandFromClientDto;
import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.exceptions.DataCantBeSentException;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;
import com.alisha.common.utilities.Pair;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;

@SuppressWarnings("FieldCanBeLocal")
public class ServerApp {
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final Logger logger;
    private final int countOfBytesForSize = 4;
    private final int serverWaitingPeriod = 50;
    private String stringData;
    private final int timeoutToSend = 10;

    public ServerApp(HistoryManager historyManager, CollectionManager collectionManager, FileManager fileManager, Logger logger) {
        this.logger = logger;
        this.collectionManager = collectionManager;
        this.historyManager = historyManager;
        this.fileManager = fileManager;
    }

    public void start(int serverPort, String serverIp) throws IOException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            initialise(datagramChannel, serverIp, serverPort);
            boolean isWorkingState = true;
            datagramChannel.configureBlocking(false);
            Scanner scanner = new Scanner(System.in);
            while (isWorkingState) {
                if (System.in.available() > 0) {
                    final String inp = scanner.nextLine();
                    if ("exit".equals(inp)) {
                        isWorkingState = false;
                    }
                    if ("save".equals(inp)) {
                        System.out.println(new SaveCommand(fileManager).execute(collectionManager, historyManager));
                    }
                }
                byte[] amountOfBytesHeader = new byte[countOfBytesForSize];
                ByteBuffer amountOfBytesHeaderWrapper = ByteBuffer.wrap(amountOfBytesHeader);
                SocketAddress clientSocketAddress = datagramChannel.receive(amountOfBytesHeaderWrapper);
                if (Objects.nonNull(clientSocketAddress)) {
                    Command command = receive(amountOfBytesHeader, datagramChannel);
                    CommandResultDto commandResultDto = command.execute(collectionManager, historyManager);
                    logger.info("executed the command with result: " + commandResultDto.toString());
                    send(commandResultDto, datagramChannel, clientSocketAddress);
                }
            }
            System.out.println(new SaveCommand(fileManager).execute(collectionManager, historyManager));
        } catch (DataCantBeSentException | InterruptedException e) {
            logger.info("Could not send data to client");
        } catch (BindException e) {
            logger.error("Could not use these ports and ip, bind exception. Please re-start server with another arguments");
        } catch (IOException | IllegalArgumentException | IllegalStateException e) {
            logger.error("There was a problem with a datafile. Please check if it is available.");
        }
    }

    private void send(CommandResultDto commandResultDto, DatagramChannel datagramChannel, SocketAddress clientSocketAddress) throws IOException, DataCantBeSentException {
        // Send
        Pair<byte[], byte[]> pair = serialize(commandResultDto);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();


        try {
            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            int limit = timeoutToSend;
            while (datagramChannel.send(sendDataAmountWrapper, clientSocketAddress) <= 0) {
                limit -= 1;
                logger.info("could not sent a package, re-trying");
                if (limit == 0) {
                    throw new DataCantBeSentException();
                }
            } // ������� ������������ ����-���������� ������ � �������� ������� ������
            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            while (datagramChannel.send(sendBuffer, clientSocketAddress) <= 0) {
                limit -= 1;
                logger.info("could not send a package, re-trying");
                if (limit == 0) {
                    throw new DataCantBeSentException();
                }
            }
            logger.info("sent the command result to the client");
        } catch (IOException e) {
            logger.error("could not send the data to client because the message is too big");
        }


    }

    private Command receive(byte[] amountOfBytesHeader, DatagramChannel datagramChannel) throws IOException, InterruptedException {
        // Receive
        byte[] dataBytes = new byte[bytesToInt(amountOfBytesHeader)];

        ByteBuffer dataBytesWrapper = ByteBuffer.wrap(dataBytes);

        Thread.sleep(serverWaitingPeriod);

        SocketAddress checkAddress = datagramChannel.receive(dataBytesWrapper);
        while (checkAddress == null) {
            checkAddress = datagramChannel.receive(dataBytesWrapper);
        }

        CommandFromClientDto commandFromClientDto;
        try {
            commandFromClientDto = (CommandFromClientDto) deserialize(dataBytes);
        } catch (ClassNotFoundException e) {
            return new HelpCommand();
        }
        logger.info("received a data object: " + commandFromClientDto.getCommand().toString());
        return (commandFromClientDto).getCommand();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    /**
     * @return first - data itself, second - amount of bytes in data
     */
    public Pair<byte[], byte[]> serialize(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(obj);
        byte[] sizeBytes = ByteBuffer.allocate(countOfBytesForSize).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes); // � ������ 4 ������ ����� ��������� �����-���������� ������ �����������
    }

    public static int bytesToInt(byte[] bytes) {
        final int vosem = 8;
        final int ff = 0xFF;

        int value = 0;
        for (byte b : bytes) {
            value = (value << vosem) + (b & ff);
        }
        return value;
    }

    private void initialise(DatagramChannel datagramChannel, String serverIp, int serverPort) throws IOException {
        datagramChannel.bind(new InetSocketAddress(serverIp, serverPort));
        logger.info("Made a datagram channel with ip: " + serverIp);
        stringData = fileManager.read();
        TreeSet<Route> routes = new JsonParser().deSerialize(stringData);
        collectionManager.initialiseData(routes);
        logger.info("Initialized collection, ready to receive data.");
    }
}
