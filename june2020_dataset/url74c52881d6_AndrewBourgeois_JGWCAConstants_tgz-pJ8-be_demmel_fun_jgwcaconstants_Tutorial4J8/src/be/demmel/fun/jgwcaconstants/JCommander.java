package be.demmel.fun.jgwcaconstants;

import be.demmel.fun.jgwcaconstants.bll.GWCAConnection;
import be.demmel.fun.jgwcaconstants.bll.GWCAPacket;
import be.demmel.fun.jgwcaconstants.bll.NamedPipeGWCAConnection;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperation;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JCommander {

    private static final Logger LOG = LoggerFactory.getLogger(JCommander.class);
    private static final String HEX = "HEX", FLOAT = "FLOAT", INT = "INT";
    private static GWCAConnection gwcaConnection;
    private static JComboBox cb, cbType;
    private static JTextField wTextField, lTextField;
    private static JLabel wLabel, lLabel;

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("Startup");
        try {
            LOG.info("The \"Java GWCAConstants\" (version {}) is starting", Version.getVersion());
            addShutdownHook();

            // Fill in the PID here
            gwcaConnection = new NamedPipeGWCAConnection(new File("\\\\.\\pipe\\GWCA_" + 4252));
            gwcaConnection.open();

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            // open jframe with some combobox and stuff
            JFrame window = new JFrame("JGWCACommander");
            window.setSize(325, 140);
            window.setResizable(false);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setLayout(null);

            // make sure the window appears in the middle of the screen
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int w = window.getSize().width;
            int h = window.getSize().height;
            int x = (dim.width - w) / 2;
            int y = (dim.height - h) / 2;
            window.setLocation(x, y);

            window.setVisible(true);

            cb = new JComboBox(GWCAOperation.values());
            cb.setBounds(5, 5, 310, 25);
            window.getContentPane().add(cb);

            wTextField = new JTextField();
            wTextField.setBounds(5, 35, 155, 25);
            window.getContentPane().add(wTextField);

            lTextField = new JTextField();
            lTextField.setBounds(5, 60, 155, 25);
            window.getContentPane().add(lTextField);

            wLabel = new JLabel();
            wLabel.setBounds(170, 35, 145, 25);
            window.getContentPane().add(wLabel);

            lLabel = new JLabel();
            lLabel.setBounds(170, 60, 145, 25);
            window.getContentPane().add(lLabel);

            JButton sendButton = new JButton("Send");
            sendButton.setBounds(5, 85, 105, 25);
            window.getContentPane().add(sendButton);

            cbType = new JComboBox(new String[]{HEX, FLOAT, INT});
            cbType.setBounds(230, 85, 100, 25);
            window.getContentPane().add(cbType);

            sendButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    send();
                }
            });

            JButton sendAndReceiveButton = new JButton("Send & Receive");
            sendAndReceiveButton.setBounds(110, 85, 105, 25);
            window.getContentPane().add(sendAndReceiveButton);

            sendAndReceiveButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    send();
                    receive();
                }
            });

            window.repaint();
            //cb.getSelectedItem();
            LOG.info("The \"Java GWCAConstants\" started");
            while (true) {
                Thread.sleep(Long.MAX_VALUE);
            }
        } catch (Throwable throwable) {
            LOG.error("Initializing the \"Java GWCAConstants\" failed because: ", throwable);
        } finally {
            try {
                if (gwcaConnection != null) {
                    gwcaConnection.close();
                }
            } catch (IOException ex) {
                LOG.warn("Failed to close the connection: ", ex);
            }
        }
    }

    private static void send() {
        wLabel.setText("- VOID -");
        lLabel.setText("- VOID -");

        GWCAOperation operationToInvoke = (GWCAOperation) cb.getSelectedItem();
        String parameterType = (String) cbType.getSelectedItem();
        String wInput = wTextField.getText();
        String lInput = lTextField.getText();

        LOG.debug("Invoking {}", operationToInvoke);
        LOG.debug("Parameter types: {}", parameterType);
        byte[] w = null, l = null;

        if (HEX.equals(parameterType)) {
            w = hexToByteArray(wInput);
            l = hexToByteArray(lInput);
        } else if (INT.equals(parameterType)) {
            w = intToByteArray(wInput);
            l = intToByteArray(lInput);
        } else if (FLOAT.equals(parameterType)) {
            w = floatToByteArray(wInput);
            l = floatToByteArray(lInput);
        } else {
            LOG.error("Impossible!");
        }
        try {
            gwcaConnection.sendPacket(new GWCAPacket(operationToInvoke, w, l));
        } catch (IOException ex) {
            LOG.warn("Failed: ", ex);
        }

        LOG.debug("Invoked");
    }

    private static void receive() {
        LOG.debug("Receiving packet");
        String parameterType = (String) cbType.getSelectedItem();
        LOG.debug("Parameter types: {}", parameterType);

        try {
            GWCAPacket gwcaPacket = gwcaConnection.receivePacket();
            byte[] w = null, l = null;

            if (HEX.equals(parameterType)) {
                //TODO: Hex format
                wLabel.setText(Integer.toString(byteArrayToInt(gwcaPacket.getWparam())));
                lLabel.setText(Integer.toString(byteArrayToInt(gwcaPacket.getLparam())));
            } else if (INT.equals(parameterType)) {
                wLabel.setText(Integer.toString(byteArrayToInt(gwcaPacket.getWparam())));
                lLabel.setText(Integer.toString(byteArrayToInt(gwcaPacket.getLparam())));
            } else if (FLOAT.equals(parameterType)) {
                wLabel.setText(Float.toString(byteArrayToFloat(gwcaPacket.getWparam())));
                lLabel.setText(Float.toString(byteArrayToFloat(gwcaPacket.getLparam())));
            } else {
                LOG.error("Impossible!");
            }

        } catch (IOException ex) {
            LOG.warn("Failed: ", ex);
        }

        LOG.debug("Received");
    }

    private static int byteArrayToInt(byte[] b) {
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }

    private static float byteArrayToFloat(byte[] b) {
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getFloat();
    }

    private static byte[] floatToByteArray(String f) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putFloat(Float.parseFloat(f.isEmpty() ? "0" : f));
        return bb.array();
    }

    private static byte[] intToByteArray(String i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(Integer.parseInt(i.isEmpty() ? "0" : i));
        return bb.array();
    }

    private static byte[] hexToByteArray(String i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(Integer.parseInt(i.isEmpty() ? "0" : i, 16));
        return bb.array();
    }

    // Adds a shutdownHook to the JVM instance that will (in most cases) get called when the application is shutting down
    // it logs the event and does necessary cleanup (if any)
    private static void addShutdownHook() throws Throwable {
        LOG.info("Adding ShutdownHook");
        try { // adding shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));
            LOG.info("ShutdownHook added");
        } catch (Throwable t) {
            LOG.error("Adding the ShutdownHook failed because: ", t);
            throw t;
        }
    }
}
