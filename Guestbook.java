package com.javadatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** Ett grafiskt gränssnitt som används för att interagera med en databas genom att dels se data i tabellerna (kommentarer) och skapa nya inlägg i den (kommentarer).
 * @Author Nephixium
 * @Version 1.0
 * */

public class Guestbook implements Runnable {

    private JFrame frame;
    private DBHandler dbConnection = new DBHandler();

    @Override
    public void run() {
        frame = new JFrame("Guestbook");
        frame.setPreferredSize(new Dimension(640,600));

        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Shutting down..");
                System.exit(0);
            }
        });

        createElements(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    /** Skapar alla grafiska element som gränssnittet omfattar. Metoden använder sig av GridBagConstraints som ger möjligheter att mer direkt hantera placering av olika delar genom ankare, indrag och storlek. */
    public void createElements(Container container) {

        GridBagLayout f = new GridBagLayout();
        GridBagConstraints a = new GridBagConstraints();
        container.setLayout(f);

        JButton postComment = new JButton("Post comment");
        JButton clear = new JButton("Clear");
        JTextField name = new JTextField(15);
        JTextField email = new JTextField(15);
        JTextField website = new JTextField(15);
        JTextArea comment = new JTextArea(2,4);
        JTextArea reader = new JTextArea(12, 4);
        comment.setLineWrap(true);
        reader.setLineWrap(true);
        reader.setEditable(false);
        JScrollPane commentScroll = new JScrollPane(comment);
        JScrollPane readerScroll = new JScrollPane(reader);
        comment.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        a.gridx = 0; // första kolumnen
        a.gridy = 1; // första raden
        a.insets.set(0,0,3,5);
        a.anchor = GridBagConstraints.LINE_END;
        container.add(new JLabel("Name: "),a);

        a.gridx = 0; // första kolumnen
        a.gridy = 2; // andra raden
        a.insets.set(3,0,0,5);
        a.anchor = GridBagConstraints.LINE_END;
        container.add(new JLabel("Email: "),a);

        a.gridx = 0;
        a.gridy = 3;
        a.insets.set(6,0,0,5);
        a.anchor = GridBagConstraints.LINE_END;
        container.add(new JLabel("Website: "),a);

        a.gridx = 1;
        a.gridy = 1;
        a.insets.set(0,0,3,0);
        container.add(name,a);

        a.gridx = 1;
        a.gridy = 2;
        a.insets.set(3,0,0,0);
        container.add(email,a);

        a.gridx = 1;
        a.gridy = 3;
        a.insets.set(6,0,0,0);
        a.anchor = GridBagConstraints.LINE_END;
        container.add(website,a);

        a.gridx = 0;
        a.gridy = 4;
        a.fill = GridBagConstraints.VERTICAL;
        a.insets.set(20,0,0,0);
        container.add(clear,a);
        clear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a) {
                name.setText("");
                email.setText("");
                website.setText("");
            }
        });

        a.gridx = 0;
        a.gridy = 5;
        a.gridwidth = 4;
        a.gridheight = 4;
        a.insets.set(20,18,0,0);
        a.fill = GridBagConstraints.BOTH;
        container.add(commentScroll,a);

        a.gridx = 0;
        a.gridy = 10;
        a.gridwidth = 4;
        a.gridheight = 4;
        a.insets.set(20,18,0,0);
        a.fill = GridBagConstraints.BOTH;
        container.add(readerScroll,a);
        dbConnection.getAllEntries(reader);

        a.gridx = 1;
        a.gridy = 4;
        a.anchor = GridBagConstraints.CENTER;
        a.insets.set(20,0,0,0);
        container.add(postComment,a);
        postComment.addActionListener(new ActionListener() {

            /** Skapar en ny kommentar och lagrar den i databasen om fälten är ifyllda */
            @Override
            public void actionPerformed(ActionEvent b) {
                if (!name.getText().equals("") && !email.getText().equals("") && !website.getText().equals("") && !comment.getText().equals("")) {
                    Author newAuthor = new Author(name.getText(), email.getText(), website.getText());
                    Comment newComment = new Comment(newAuthor, comment.getText());
                    dbConnection.postComment(newAuthor, newComment);

                } else {
                    System.out.println("Name, email and comment cannot be empty!");
                }
            } });
    }

    public static void main(String[] args) {
        new Thread (new Guestbook()).start();
    }
}


