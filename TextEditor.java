/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author shaheerimran
 */
public class TextEditor {
    
    public static void main (String[] args) throws IOException{
        //Declaring all variables
        JFrame TextWindow = new JFrame("TextEditor");
        JPanel TextGui = new JPanel();
        JTextArea TextArea = new JTextArea();
        JButton TextSaveAs = new JButton("Save as");
        JButton TextOpen = new JButton("Open File");
        JButton TextSave = new JButton("Save");
        
        //Setting up preferences for window
        TextWindow.setSize(800, 600);
        TextArea.setSize(TextWindow.getWidth(), TextWindow.getHeight());
        TextWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TextGui.setLayout(new BorderLayout());
        TextGui.add(TextSaveAs, BorderLayout.NORTH);
        TextGui.add(TextOpen, BorderLayout.SOUTH);
        TextGui.add(TextSave, BorderLayout.EAST);
        TextGui.add(TextArea, BorderLayout.CENTER);
        TextWindow.add(TextGui);
        TextWindow.setLocationRelativeTo(null);
        
        //Listening for when to save file
        class TextSaveAsListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    save(TextArea, TextWindow);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        class TextSaveListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                JFrame saveWindow = new JFrame("Save");
                JPanel saveGUI = new JPanel();
                JTextArea saveName = new JTextArea(1, 10);
                JButton saveButton = new JButton("Save");
                
                saveGUI.setLayout(new FlowLayout());
                saveGUI.add(saveName);
                saveGUI.add(saveButton);
                
                saveWindow.add(saveGUI);
                saveWindow.setLocationRelativeTo(TextWindow);
                saveWindow.setSize(400, 300);
                
                class SaveButtonListener implements ActionListener{
                    @Override
                    public void actionPerformed(ActionEvent e){
                        File saveFile = new File(saveName.getText());
                        try {          
                            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                            String currentLine;
                            try {
                                while((currentLine = reader.readLine()) != null){
                                    currentLine.trim();
                                }
                                reader.close();
                            } catch (IOException ex) {
                                Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        PrintWriter pWriter = null;
                        try {
                            pWriter = new PrintWriter (saveName.getText());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        pWriter.println(TextArea.getText()); 
                        pWriter.close();
                        saveWindow.dispose();
                    }
                }
                saveButton.addActionListener(new SaveButtonListener());
                
                saveWindow.setVisible(true);
            }
        }
        
        class TextOpenListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame openWindow = new JFrame("Open File");
                JPanel openGui = new JPanel();
                JTextArea openFile = new JTextArea(1, 10);
                JButton open = new JButton("Open");
                openWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
                openWindow.setSize(400, 300);
                openWindow.setLocationRelativeTo(TextWindow);
                openGui.setLayout(new FlowLayout());
                openGui.add(openFile);
                openGui.add(open);
                openWindow.add(openGui);
                
                class OpenListener implements ActionListener{
                    public void actionPerformed(ActionEvent e){
                        try {
                            openFile(openFile, TextArea);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                open.addActionListener(new OpenListener());
                openWindow.setVisible(true);
            }
        }
         
        //Starting the listening session
        TextSaveAs.addActionListener (new TextSaveAsListener());
        TextOpen.addActionListener (new TextOpenListener());
        TextSave.addActionListener(new TextSaveListener());
        
        //Show window
        TextWindow.setVisible(true);
        
        
    }
    
    
    public static void save(JTextArea textbox, JFrame relativeWindow) throws IOException{
        JFrame saveWindow = new JFrame("Save");
        JPanel savePanel = new JPanel();
        JTextArea saveAs = new JTextArea(1, 10);
        JButton OK = new JButton("Save as");
        saveWindow.setSize(400, 300);
        saveWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        saveWindow.setLocationRelativeTo(relativeWindow);
        savePanel.setLayout(new FlowLayout());
        savePanel.add(saveAs);
        savePanel.add(OK);
        saveWindow.add(savePanel);
        
        class OKListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    PrintWriter pWriter = new PrintWriter (saveAs.getText());
                    pWriter.println(textbox.getText()); 
                    pWriter.close();
                    saveWindow.dispose();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        OK.addActionListener(new OKListener());
        
        saveWindow.setVisible(true);
        
    }
    
    //Open a saved file
    public static void openFile(JTextArea textArea, JTextArea editor) throws FileNotFoundException, IOException{
        //Used for finding lines with text in them and lines without
        String line = null;
        
        //Setting up the reader of the text file
        FileReader fileReader = new FileReader(textArea.getText());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        //Checking which lines have text
        while((line = bufferedReader.readLine()) != null){
            //Scanning lines and adding them to the text area
            editor.append(line);
        }
        
        //ALWAYS CLOSE THE READER AND/OR WRITER
        bufferedReader.close();
    }
}


