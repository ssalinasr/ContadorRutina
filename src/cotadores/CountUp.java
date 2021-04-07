/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cotadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JOptionPane;





/**
 *
 * @author Julian Sanchez
 */
public class CountUp {
    
    private Timer timer;
    
    private int exercising;
    private int relaxing;
    
    private int second;
    protected int minute;
    protected int limit;
    protected int counter;
    
    private int breakTime;
    private int exerciseTime;
    
    private boolean exerciseFlag;
    private boolean relaxFlag;
    
    private JLabel State; 
    private JButton btn;

    public CountUp() {
        countUpTimer();
        timer.start();
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    public void setExercising(int exercising) {
        this.exercising = exercising;
    }

    public void setRelaxing(int relaxing) {
        this.relaxing = relaxing;
    }

    public void assignJLabel(JLabel State) {
        this.State = State;
    }
    
     public void assignJButton(JButton btn) {
        this.btn = btn;
    }
    
    public void countUpTimer() {
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btn.setEnabled(false);
                if(limit == 0){
                    limit = 5+(60 * minute);
                }
                counter++;
                second ++;
                System.out.println(counter);
                System.out.println(limit);
                
            if(counter == limit){
                ((Timer)ae.getSource()).stop();
                btn.setEnabled(true);
                JOptionPane.showMessageDialog(null,"Tiempo finalizado.","Información",1);
                    }
            
                if(second == 6){
                   exerciseFlag = true;
                   State.setText("¡EXERCISING!");
                   ReproducirSonido("src/recursos/beep-07.wav");
                  
                } else if (second < 5) {
                    State.setText("ARE YOU READY?"); 
                }
                
                if(exerciseTime == 2*((int)(exercising / 3)) || breakTime == 2*((int)(relaxing /3))){
                   ReproducirSonido("src/recursos/transition-06.wav"); 
                }
                
                
                if (exerciseFlag == true){
                    exerciseTime ++;
                    if (exerciseTime == exercising ){
                        exerciseFlag = false;
                        relaxFlag = true;
                        exerciseTime = 0;
                        State.setText("Flojeanding");
                        ReproducirSonido("src/recursos/beep-07.wav");
                    }
                } else if (relaxFlag ==true){
                    breakTime++;
                    if (breakTime == relaxing){
                        exerciseFlag = true;
                        relaxFlag = false;
                        breakTime = 0;
                        State.setText("¡EXERCISING!");
                        ReproducirSonido("src/recursos/beep-07.wav");
                    } 
                }
            }    
        });
    }
    
    public void ReproducirSonido(String nombreSonido) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al reproducir el sonido."+ ex);
            
        }
    }
    
}
