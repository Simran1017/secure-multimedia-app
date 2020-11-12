package com.socket;

//import com.ui.ChatFrame;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;


public class Download implements Runnable{
    
    public ServerSocket server;
    public Socket socket;
    public int port;
    public String saveTo = "";
    public InputStream In;
    public OutputStream o ;
    public FileOutputStream Out , Out1 ;
    public ChatFrame ui;
    public  InputStream In1 ;
    public String encTo ;
    
     int total_length = 0 , count = 0 , i = 0 , max_size = 1024;
     
    public AesAlgo ae ;
  
       
    public Download(String saveTo, ChatFrame ui) throws Exception{ //for aesAlgo 
        try {
            server = new ServerSocket(0);
            port = server.getLocalPort();
            this.saveTo = saveTo;
            this.ui = ui;
            
            ae = new AesAlgo("ABCDEFGHIJKLMNOP") ;
	    File file = new File(this.saveTo);
            String name = file.getName();
            encTo = name+".encrypted" ;
            
        } 
        catch (IOException ex) {
            System.out.println("Exception [Download : Download(...)]");
        }
    }

    @Override
    public void run() {
       try {
            socket = server.accept();
            System.out.println("Download : "+socket.getRemoteSocketAddress());
            
             
            In = socket.getInputStream();
            
            In1 = socket.getInputStream() ; //extra
            
            Out = new FileOutputStream(saveTo);
            Out1 = new FileOutputStream(encTo);
            
            byte[] buffer = new byte[1024 * 3];
          
            count =0 ; i = 0 ;
  
           while ((count = In.read(buffer)) >= 0) {
               
              
               byte[] realBuff = Arrays.copyOf(buffer, count);
               
               i++;

                if(JOptionPane.showConfirmDialog(ui, ("Accept packet "+i+" ?")) == 0)
                {    
                       String str = new String(realBuff);
                       System.out.println("string \n " + str + " \n ");

                        Out1.write( realBuff , 0 , realBuff.length) ;


                        String decryptedText1 = ae.decrypt(str, "ABCDEFGHIJKLMNOP".getBytes());
                        System.out.println("decryptedText1 : " + decryptedText1 + "\n");

                        byte[] b = decryptedText1.getBytes();

                        Out.write(b, 0, b.length);
                        System.out.println("Packet " + i + " received \n");
                }
                
            }
           
            Out.flush();
            In.close();
            
            ui.jTextArea1.append("\n\n[Application > Me] : DOWNLOAD COMPLETE\n");
            
            if(Out != null){ Out.close(); }
            if(In != null){ In.close(); }
            if(socket != null){ socket.close(); }
        } 
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exception [Download : run(...)]");
        }
    }
            
} 




















//            byte[] bytes = new byte[65536];
//
//            In = socket.getInputStream();
//            Out = new FileOutputStream(saveTo);
//
//            int numToSend = 0, i = 0;
//
//            numToSend = In.read();
//
//            long numSent = 0;
//
//            while (numSent < numToSend) {
//                long numThisTime = numToSend - numSent;
//
//                numThisTime = numThisTime < bytes.length ? numThisTime : bytes.length;
//
//                int numRead = 0;
//
//                numRead = In.read(bytes, 0, (int) numThisTime);
//
//                if (numRead != -1) {
//                    break;
//                }
//
//                i++;
//                if (JOptionPane.showConfirmDialog(ui, ("Accept packet " + i + ":" + numRead)) == 0) {
//                    Out.write(bytes, 0, numRead);
//                }
//
//                numSent += numRead;
//            }
//
//        } catch (Exception ae) {
//        }
//    }
//}











//            while(true)
//			{
//				
//			DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
//		        ds2.receive(dp1);
//		    
//		        DatagramPacket rcd= new DatagramPacket(b3, b3.length);
//			ds3.receive(rcd);
//			String rcd_msg=new String(b3,0,rcd.getLength());
//				String new_rcd="";
//				for(int n=3;n<rcd_msg.length();n++)
//					new_rcd=new_rcd+rcd_msg.charAt(n);
//				ui.jTextArea1.append("You: Block "+new_rcd+" received\n");
//                             String mid=new String(b1,0,dp1.getLength());
//
//			    pw.write(mid);
//
//				
//		        ui.jTextArea1.append("Other: File Sent \n");
//			}
//        }catch(Exception e) 
//        {
//            e.printStackTrace();}
//			}
//    }
        






























//            byte b[] = new byte[10000]; //reads 1kh byte data at a time from the file
//            int x=1 , j=0 ;
//            String s ="";
//            int read_bytes ;
//            In = socket.getInputStream() ;
//            while(In.available()!=0)
//          {   s = "" ;
//                if( x<= 9) //for making parts name
//                {
//                    s = saveTo+".00"+x ;
//                }
//                else
//                {
//                    s = saveTo+".0"+x ;
//                }
//                
//        
//                Out = new FileOutputStream(s) ;
//           //     o = new FileOutputStream(saveTo) ;
//          //  FileInputStream In = new FileInputStream(file) ;
//            
//    
//                
//                while( j<=50000 && In.available()!=0) //creates equal size file parts
//                {
//                    read_bytes = In.read(b, 0, 10000);
//                    j=j+read_bytes ; //to keep record of how much file it has read already
//                    //if(JOptionPane.showConfirmDialog(ui, ("Accept ?")) == 0)
//                        Out.write(b, 0, read_bytes);
//                    
//                }
//                ui.jTextArea1.append("part"+x+"created") ;
//                x++ ;
//            }
//            System.out.println("file split1 successfully");
//         //   In.close();
//            
//            ui.jTextArea1.append("[Application > Me] : Download complete\n");
//            
//            if(Out != null){ Out.close(); }
//            if(In != null){ In.close(); }
//            if(socket != null){ socket.close(); }
//        } 
//        catch (Exception ex) {
//            System.out.println("Exception [Download : run(...)]");
//        }
//    }
//}





//            while(true)
//			{
//				
//			DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
//		        ds2.receive(dp1);
//		    
//		        DatagramPacket rcd= new DatagramPacket(b3, b3.length);
//				ds3.receive(rcd);
//				String rcd_msg=new String(b3,0,rcd.getLength());
//				String new_rcd="";
//				for(int n=3;n<rcd_msg.length();n++)
//					new_rcd=new_rcd+rcd_msg.charAt(n);
//				ui.jTextArea1.append("You: Block "+new_rcd+" received\n");
//                             String mid=new String(b1,0,dp1.getLength());
//
//			    pw.write(mid);
//
//				
//		        ui.jTextArea1.append("Other: File Sent \n");
//			}
//        }catch(Exception e) 
//        {
//            e.printStackTrace();}
//			}
//    }
//        






























//            byte b[] = new byte[10000]; //reads 1kh byte data at a time from the file
//            int x=1 , j=0 ;
//            String s ="";
//            int read_bytes ;
//            In = socket.getInputStream() ;
//            while(In.available()!=0)
//          {   s = "" ;
//                if( x<= 9) //for making parts name
//                {
//                    s = saveTo+".00"+x ;
//                }
//                else
//                {
//                    s = saveTo+".0"+x ;
//                }
//                
//        
//                Out = new FileOutputStream(s) ;
//           //     o = new FileOutputStream(saveTo) ;
//          //  FileInputStream In = new FileInputStream(file) ;
//            
//    
//                
//                while( j<=50000 && In.available()!=0) //creates equal size file parts
//                {
//                    read_bytes = In.read(b, 0, 10000);
//                    j=j+read_bytes ; //to keep record of how much file it has read already
//                    //if(JOptionPane.showConfirmDialog(ui, ("Accept ?")) == 0)
//                        Out.write(b, 0, read_bytes);
//                    
//                }
//                ui.jTextArea1.append("part"+x+"created") ;
//                x++ ;
//            }
//            System.out.println("file split1 successfully");
//         //   In.close();
//            
//            ui.jTextArea1.append("[Application > Me] : Download complete\n");
//            
//            if(Out != null){ Out.close(); }
//            if(In != null){ In.close(); }
//            if(socket != null){ socket.close(); }
//        } 
//        catch (Exception ex) {
//            System.out.println("Exception [Download : run(...)]");
//        }
//    }
//}
































//package com.socket;
//
//import com.ui.ChatFrame;
//import java.io.*;
//import java.net.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Download implements Runnable{
//    
//    public ServerSocket server;
//    public Socket socket;
//    public int port;
//    public String saveTo = ""; //FILENAME
//    public InputStream In;
//    public FileOutputStream Out;
//    public ChatFrame ui;
//    
//    public Download(String saveTo, ChatFrame ui){
//        try {
//            server = new ServerSocket(0);
//            port = server.getLocalPort();
//            this.saveTo = saveTo;
//            this.ui = ui;
//        } 
//        catch (IOException ex) {
//            System.out.println("Exception [Download : Download(...)]");
//        }
//    }
//
//    @Override
//    public void run() {
//        try {
//         socket = server.accept();
//        System.out.println("Download : "+socket.getRemoteSocketAddress());
//            try {
//                //
//                In = socket.getInputStream();
//            } catch (IOException ex) {
//                Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                Out = new FileOutputStream(saveTo);
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
////            byte[] buffer = new byte[1024];
////            int count;
////            
////            while((count = In.read(buffer)) >= 0){
////                Out.write(buffer, 0, count);
////            }
////            
////            Out.flush();
//
//        File ofile = new File(saveTo);
//
////        FileOutputStream fos;
////
////        FileInputStream fis;
//
//        byte[] fileBytes;
//
//        int bytesRead = 0;
//
//        List<File> list = new ArrayList<File>();
//
//        list.add(new File(saveTo+".part0"));
//
//        list.add(new File(saveTo+".part1"));
//
//        list.add(new File(saveTo+".part2"));
//
//        list.add(new File(saveTo+".part3"));
//
//        list.add(new File(saveTo+".part4"));
//
//        list.add(new File(saveTo+".part5"));
//
//        list.add(new File(saveTo+".part6"));
//
//        list.add(new File(saveTo+".part7"));
//
//       
//
//           Out = new FileOutputStream(ofile,true);
//
//           for (File file : list) {
//
//                In = new FileInputStream(file);
//
//                fileBytes = new byte[(int) file.length()];
//
//                bytesRead = In.read(fileBytes, 0,(int)  file.length());
//
//                assert(bytesRead == fileBytes.length);
//
//                assert(bytesRead == (int) file.length());
//
//                Out.write(fileBytes);
//
//                Out.flush();
//
//                fileBytes = null;
//
////                fis.close();
//
//                Out = null;
//
//            }
//
////            fos.close();
////
////            fos = null;
//            
//            ui.jTextArea1.append("[Application > Me] : Download complete\n");
//            
//            if(Out != null){ Out.close(); }
//            if(In != null){ In.close(); }
//            if(socket != null){ socket.close(); }
//        } 
//        catch (Exception ex) {
//            System.out.println("Exception [Download : run(...)]");
//        }
//    }//
//}