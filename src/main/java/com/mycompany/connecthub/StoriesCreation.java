/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.connecthub;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class StoriesCreation extends javax.swing.JFrame {
    
    File f1;

    /**
     * Creates new form StoriesCreation
     */
    public StoriesCreation() {
        
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        storyText = new javax.swing.JTextField();
        addimageButton = new javax.swing.JButton();
        storyLabel = new javax.swing.JLabel();
        storyButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adding Stories");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        storyText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storyTextActionPerformed(evt);
            }
        });

        addimageButton.setBackground(new java.awt.Color(51, 153, 255));
        addimageButton.setForeground(new java.awt.Color(255, 255, 255));
        addimageButton.setText("Add Image");
        addimageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addimageButtonActionPerformed(evt);
            }
        });

        storyLabel.setBackground(new java.awt.Color(255, 255, 255));
        storyLabel.setOpaque(true);

        storyButton.setBackground(new java.awt.Color(51, 153, 255));
        storyButton.setForeground(new java.awt.Color(255, 255, 255));
        storyButton.setText("Add Story");
        storyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storyButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Enter Content");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(storyText))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(storyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addimageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(storyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(111, 111, 111))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(storyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addimageButton))
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(storyButton)
                    .addComponent(jLabel2)
                    .addComponent(storyText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void storyTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storyTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_storyTextActionPerformed
//button to add image to the story
    private void addimageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addimageButtonActionPerformed
        //select file to add image to the story
        JFileChooser file = new JFileChooser();
        int value = file.showOpenDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
            f1 = file.getSelectedFile();
            StoryDatabase s = StoryDatabase.getInstance();
            
            ImageIcon scaledIcon = s.chooseStoryImage(f1, Functionalities.currentUser);
            
            storyLabel.setIcon(scaledIcon);
        }
    }//GEN-LAST:event_addimageButtonActionPerformed

    private void storyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storyButtonActionPerformed
        Story s;
        String xInput = storyText.getText();
        Factory f = new Factory();
        if(f1==null && xInput.equals("") )
        {
            JOptionPane.showMessageDialog(this,"you can not upload an empty Story");
        }
        else{
        //check if file exists to set image path with file path or withnull
        if (f1 != null) {
            s = (Story) f.setContent("Story", Functionalities.currentUser.getUserId(), xInput, f1.getAbsolutePath(), LocalDateTime.now());
        } else {
            s = (Story) f.setContent("Story", Functionalities.currentUser.getUserId(), xInput, "null", LocalDateTime.now());
        }
        ArrayList<Story> stories = StoryDatabase.readStories();
        stories.add(s);
        StoryDatabase.saveStories(stories);
        this.setVisible(false);
        NewsFeed newsfeed = new NewsFeed();
        newsfeed.setVisible(true);
        }
    }//GEN-LAST:event_storyButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        NewsFeed newsfeed = new NewsFeed();
        newsfeed.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StoriesCreation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StoriesCreation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StoriesCreation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StoriesCreation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StoriesCreation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addimageButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton storyButton;
    private javax.swing.JLabel storyLabel;
    private javax.swing.JTextField storyText;
    // End of variables declaration//GEN-END:variables
}
