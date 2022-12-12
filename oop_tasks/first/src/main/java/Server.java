import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class.getName());
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public static void main(String[] args) {
        new Server().run();
    }

    protected void setUp() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 4545));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public Server(){}

    public void run() {
        try{
            setUp();
            processKeys();
        } catch (IOException e){
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }

    public void processKeys() throws IOException {
        while (true){
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    register();
                }
                if(selectionKey.isReadable()){
                    deserializeAndRespond(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    public void register() throws IOException {
        log.info("Connection accepted");
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    public void deserializeAndRespond(SelectionKey selectionKey) throws IOException {
        SocketChannel client = (SocketChannel) selectionKey.channel();
        Serializable clientObject;
        boolean received = false;

        try{
            ByteBuffer buffer = readToBuffer(client);
            clientObject = readObject(buffer);
            received = true;
            log.info(clientObject.toString());
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, "Exception: ", ex);
        }
        respond(received, client);
        client.close();
    }

    protected void respond(boolean received, SocketChannel client) {
        try {
            if(received) {
                client.write(ByteBuffer.wrap("Received student".getBytes()));
            } else {
                client.write(ByteBuffer.wrap("Something went wrong".getBytes()));
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }

    ByteBuffer readToBuffer(SocketChannel client) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        return buffer;
    }

    Serializable readObject(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        buffer.rewind();
        ObjectInputStream reader = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
        return (Serializable) reader.readObject();
    }
}
