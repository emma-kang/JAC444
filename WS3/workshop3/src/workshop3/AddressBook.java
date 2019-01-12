
package workshop3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
/*
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
*/

public class AddressBook extends Application {
    TextField nameTf;
    TextField stTf;
    TextField cityTf;
    TextField stateTf;
    TextField zipTf;
    int count = 0;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) throws Exception{
        /** GUI design - GridPane */
        GridPane pane = new GridPane();
        // set the scene
        Scene scene = new Scene(pane, 800, 200);

        pane.setPadding(new Insets(20));

        // set the margin between columns and rows
        pane.setHgap(10);
        pane.setVgap(10);

        /** Set Label and Textfield */
        Label name = new Label("Name");
        pane.add(name, 0, 0);
        nameTf = new TextField();
        pane.add(nameTf, 1, 0, 8, 1);

        Label st = new Label("Street");
        pane.add(st, 0, 1);
        stTf = new TextField();
        pane.add(stTf, 1, 1, 8, 1);

        Label city = new Label("City");
        pane.add(city, 0, 2);
        cityTf = new TextField();
        pane.add(cityTf, 1, 2, 2, 1);

        Label state = new Label("State");
        pane.add(state, 3, 2);
        stateTf = new TextField();
        pane.add(stateTf, 4, 2);

        Label zip = new Label("Zip");
        pane.add(zip, 5, 2);
        zipTf = new TextField();
        pane.add(zipTf, 6, 2, 2, 1);

        HBox hbBtn = new HBox(5);

        /** Button and Event handler */
        Button addBtn = new Button("Add");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add();
                initialize();
                System.out.println("Successfully the address added in the file");
            }
        });

        Button firstBtn = new Button("First");
        firstBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                first();
            }
        });

        Button nextBtn = new Button("Next");
        nextBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                next();
            }
        });

        Button preBtn = new Button("Previous");
        preBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                previous();
            }
        });

        Button lastBtn = new Button("Last");
        lastBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                last();
            }
        });

        Button udtBtn = new Button("Update");
        udtBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update();
                System.out.println("Successfully the address updated in the file");
            }
        });

        /** Group the buttons */
        hbBtn.getChildren().addAll(
                addBtn,
                firstBtn,
                nextBtn,
                preBtn,
                lastBtn,
                udtBtn
        );

        hbBtn.setAlignment(Pos.CENTER);
        pane.add(hbBtn, 1, 4);

        primaryStage.setTitle("Address Book");
        primaryStage.setScene(scene);
        primaryStage.show(); // Display the stage
    }

    /** Write addreass to file */
    private void write(RandomAccessFile f) throws IOException {
        // Assume the size of name, street, city, state, and zip is 32, 32, 20, 2, 5 byte
        f.write(fixedLength(nameTf.getText().getBytes(), 32));
        f.write(fixedLength(stTf.getText().getBytes(), 32));
        f.write(fixedLength(cityTf.getText().getBytes(), 20));
        f.write(fixedLength(stateTf.getText().getBytes(), 2));
        f.write(fixedLength(zipTf.getText().getBytes(), 5));
    }

    /** Read address from file */
    private void read(RandomAccessFile inout) throws IOException {
        @SuppressWarnings("unused")
        int pos;
        byte[] name = new byte[32];
        pos = inout.read(name);
        nameTf.setText(new String(name));

        byte[] street = new byte[32];
        pos += inout.read(street);
        stTf.setText(new String(street));

        byte[] city = new byte[20];
        pos += inout.read(city);
        cityTf.setText(new String(city));

        byte[] state = new byte[2];
        pos += inout.read(state);
        stateTf.setText(new String(state));

        byte[] zip = new byte[5];
        pos += inout.read(zip);
        zipTf.setText(new String(zip));
    }

    /** Return a byte[] of fixed-length */
    private byte[] fixedLength(byte[] x, int n) {
        byte[] b = new byte[n];
        for (int i = 0; i < x.length; i++) {
            b[i] = x[i];
        }
        return b;
    }

    /** Initialize textfield in the stages */
    public void initialize(){
        nameTf.setText("");
        stTf.setText("");
        cityTf.setText("");
        stateTf.setText("");
        zipTf.setText("");
    }

    /** Add an address to file */
    private void add() {
        try (RandomAccessFile f = new RandomAccessFile("Address.dat", "rw")) {
            f.seek(f.length()); // move to the end of the file
            write(f); // call write method
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found " + ex);
        }
        catch (IOException ex) {
            System.out.println("IO Exception generated " + ex);
        }
        catch (IndexOutOfBoundsException ex) {}
    }

    /** Read the first address from the file */
    private void first() {
        try (RandomAccessFile f = new RandomAccessFile("Address.dat", "rw")) {
            if (f.length() > 0) {
                f.seek(0);
                read(f);
                System.out.println("This is the first address in the address book");
                count = 1;
            }
            else {
                System.out.println("There's no data yet. Please add address at least one");
            }
        }
        catch (IOException ex) {}
    }

    /** Read the next Address from the file */
    private void next() {
        try (RandomAccessFile f = new RandomAccessFile("Address.dat", "rw")) {
            if (count * 91 < f.length()) {
                // count means current index of the address book
                // assume total byte(all textfield) is 91
                f.seek(count * 91);
                read(f);
                count++;
                // because the index stars from zero
                // total number of addresses should add one
                System.out.println("This is # " + count+ " data in the address book");
            }
            else {
                System.out.println("There's no data anymore. Please go back to the first data in the file");
            }
        }
        catch (IOException ex) {}
    }

    /** Read the previous Address from the file */
    private void previous() {
        try (RandomAccessFile f = new RandomAccessFile("Address.dat", "rw")) {
            if (count > 1)
                count--;
            else
                count = 1;
            f.seek((count * 91) - 91);
            read(f);
            System.out.println("This is # " + count + " data in the address book");
        }
        catch (IOException ex) {}
    }

    /** Read last address from file */
    private void last() {
        try (RandomAccessFile f = new RandomAccessFile("Address.dat", "rw")) {
            // calculate the total number of stored addresses in the file
            count = ((int)f.length()) / 91;

            f.seek((count * 91) - 91);
            read(f);
            System.out.println("This is the last data in the address book");
        }
        catch (IOException ex) {}
    }

    /** Update and address in file */
    private void update() {
        try (RandomAccessFile f = new RandomAccessFile("Address.dat", "rw")) {
            // Move to the starting point of the target index
            f.seek(count * 91 - 91);
            write(f);
        }
        catch (FileNotFoundException ex) {}
        catch (IOException ex) {}
    }

    public static void main(String[] args) {
        launch(args);
    }

}

/*
    public void add(){
        // newline separator
        //final ByteBuffer newLine = ByteBuffer.wrap("\n".getBytes());

        // Write an address to file
        // Assume the size of name, street, city, state, and zip is 32, 32, 20, 2, 5 byte
        byte tfName[] = nameTf.getText().getBytes();
        byte tfStreet[] = stTf.getText().getBytes();
        byte tfCity[] = cityTf.getText().getBytes();
        byte tfState[] = stateTf.getText().getBytes();
        byte tfZip[] = zipTf.getText().getBytes();

        ByteBuffer nameOut = ByteBuffer.wrap(tfName);
        ByteBuffer stOut = ByteBuffer.wrap(tfStreet);
        ByteBuffer cityOut = ByteBuffer.wrap(tfCity);
        ByteBuffer stateOut = ByteBuffer.wrap(tfState);
        ByteBuffer zipOut = ByteBuffer.wrap(tfZip);

        ByteBuffer copy;

        try(FileChannel fc = (FileChannel.open(Paths.get("Address.dat"), StandardOpenOption.CREATE,
                StandardOpenOption.READ, StandardOpenOption.WRITE))){

            copy = ByteBuffer.allocate(32); // allocate in order to get name

            if(fc.size() > 0){
                // move to the end of the file
                long length = fc.size();
                fc.position(length-1);
            }
            else{
                fc.position(0);
            }
            copy.flip();

            while(copy.hasRemaining())
                fc.write(nameOut);
            copy.clear();

            copy = ByteBuffer.allocate(32); // allocate in order to get street
            while(copy.hasRemaining())
                fc.write(stOut); // read as much as buffer's byte size
            copy.clear(); // to clear buffer

            copy = ByteBuffer.allocate(20); // allocate in order to get city
            while(copy.hasRemaining())
                fc.write(cityOut);
            copy.clear();

            copy = ByteBuffer.allocate(2); // allocate in order to get state
            while(copy.hasRemaining())
                fc.write(stateOut);
            copy.clear();

            copy = ByteBuffer.allocate(5); // allocate in order to get postal code
            while(copy.hasRemaining())
                fc.write(zipOut);
            copy.clear();

            //copy.flip();

        } catch (IOException ex){
            System.out.println("IOEeception generated " + ex);
        }


    }



    /*
    public void first(){
        findData(0); // to find first data set
        System.out.println("This is the first data in the access book");
    }

    public void next(int index){
        int pos = (index*91) - 91;
        findData(pos);
        System.out.println("This is the next data");
    }

    public void previous(int index){
        int pos = (index*91) - 91;
        findData(pos);
    }

    public void last(int index){
        // numAdderess*91 means the length of the stored data
        int pos = (index*91) - 91;
        findData(pos);
        System.out.println("This is the last data");
    }

    public void update(int curIndex){
        int pos = (curIndex*91)-91;

    }

    // find data in the file
    public void findData(int pos){
        ByteBuffer copy = null;

        try(FileChannel fc = (FileChannel.open(Paths.get("Address.dat"), StandardOpenOption.READ))){

            fc.position(pos); // set specific position to read data
            copy = ByteBuffer.allocate(32); // allocate in order to get name

            while(copy.hasRemaining())
                fc.read(copy);
            String nameData = new String(copy.array());
            copy.clear();

            copy = ByteBuffer.allocate(32); // allocate in order to get street
            while(copy.hasRemaining())
                fc.read(copy); // read as much as buffer's byte size
            String stData = new String(copy.array());
            copy.clear(); // to clear buffer

            copy = ByteBuffer.allocate(20); // allocate in order to get city
            while(copy.hasRemaining())
                fc.read(copy);
            String cityData = new String(copy.array());
            copy.clear();

            copy = ByteBuffer.allocate(2); // allocate in order to get state
            while(copy.hasRemaining())
                fc.read(copy);
            String stateData = new String(copy.array());
            copy.clear();

            copy = ByteBuffer.allocate(5); // allocate in order to get postal code
            while(copy.hasRemaining())
                fc.read(copy);
            String zipData = new String(copy.array());
            copy.clear();

            // set each field as the data
            nameTf.setText(nameData);
            stTf.setText(stData);
            cityTf.setText(cityData);
            stateTf.setText(stateData);
            zipTf.setText(zipData);

        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        catch (IOException ex){
            System.out.println("IOEeception generated " + ex);
        }
    }

    */
