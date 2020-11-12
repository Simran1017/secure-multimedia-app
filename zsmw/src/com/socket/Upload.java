package com.socket;

//import com.ui.ChatFrame;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class Upload implements Runnable{

    public String addr;
    public int port;
    public Socket socket;
    public FileInputStream In , Fis;
    public InputStream In1 ;//inputstrem
    public OutputStream Out; //filepart
    public File file; //inputfile
    public ChatFrame ui; 
    int total_length = 0 , count = 0 , i = 0 , max_size = 1024;
    
    
    public AesAlgo ae ;
  
    
    public Upload(String addr, int port, File filepath, ChatFrame frame){
        super();
        try {
            file = filepath ; 
            System.out.println(filepath);
            ui = frame;
            socket = new Socket(InetAddress.getByName(addr), port);
            Out = socket.getOutputStream();
            In = new FileInputStream(filepath);
            Fis = new FileInputStream(filepath);
            
           ae = new AesAlgo("ABCDEFGHIJKLMNOP") ;

            
        } 
        catch (Exception ex) {
            System.out.println("Exception [Upload : Upload(...)]");
        }
    }
    
    @Override
    public void run() {
   try {      
         
          byte[] buffer = new byte[1024];
                    
          while( Fis.read(buffer)!= -1 )
          {
              total_length+=count;
          }
          int packets = total_length / max_size ;     
          int offset=packets*max_size;
          int last_packet_len=total_length-offset;
          if(last_packet_len<max_size)
          {
	        for(int k=last_packet_len;k<max_size;k++)
		        buffer[k]=0;
          } 
          
          Fis.close() ;
                    
          while((count = In.read(buffer)) != -1 ){
            byte[] realBuff = Arrays.copyOf( buffer , count );
          
            i++ ;  
            System.out.println("Packet " + i + " sent \n");
            String str = new String( realBuff ); 
              
            String enc = ae.encrypt( str );
            
            System.out.println(enc + " \n");
            
//            String decryptedText = ae.decrypt( encryptedText , "ABCDEFGHIJKLMNOP".getBytes());
//            System.out.println(decryptedText + "\n" ) ;
//            
            
            byte[] b = enc.getBytes() ;
            Out.write( b , 0 , b.length);
                              
            }
            Out.flush();
            
            ui.jTextArea1.append("\n\n[Applcation > Me] : FILE UPLOAD COMPLETE\n");
            ui.jButton5.setEnabled(true); ui.jButton6.setEnabled(true);
            ui.jTextField5.setVisible(true);
            
            if(In != null){ In.close(); }
            if(Out != null){ Out.close(); }
            if(socket != null){ socket.close(); }
        }
        catch (Exception ex) {
            System.out.println("Exception [Upload : run()]");
            ex.printStackTrace();
        }
    }
}

//


















//        try {
//            byte[] bytes = new byte[65536];
//            long numToSend = file.length();
//
//            Out.write((int) numToSend);
//
//            long numSent = 0;
//
//            while (numSent < numToSend) {
//                System.out.println( " success") ;
//                long numThisTime = numToSend - numSent;
//
//                numThisTime = numThisTime < bytes.length ? numThisTime : bytes.length;
//
//                int numRead = 0;
//                numRead = In.read(bytes, 0, (int) numThisTime);
//
//                if (numRead != -1) {
//                    break;
//                }
//                ui.jTextArea1.append( " sending files");
//                Out.write(bytes, 0, numRead);
//
//                numSent += numRead;
//            }
//        } catch (Exception ae) {
//        }
//    }
//}

    


        




















//
//
//try
//				{
//					//byte[] buffer = new byte[1024];
//                                        byte[] sendData=new byte[max_size];
//					while((In.read(sendData))!=-1)
//					{
//						total_length+=count;
//					}
//					int packets=total_length/max_size;
//					int offset=packets*max_size;
//					int last_packet_len=total_length-offset;
//					if(last_packet_len<max_size)
//					{
//						for(int k=last_packet_len;k<max_size;k++)
//							sendData[k]=0;
//					}		
//					In.close();
//					In1=new FileInputStream(file);
//					ui.jTextArea1.append("You: Sending block by block\n");
//					while((In1.read(sendData))!=-1)
//					{
//						if(packets<0)
//							break;
//						
//						ui.jTextArea1.append("You: Sending block "+packets+" \n");
//						String data="snt"+packets;
//						DatagramPacket snt=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getLocalHost(),port);
//						ds3.send(snt);
//					 
//						packets--;
//					}
//				}catch(Exception e1){}
//				ui.jTextArea1.append("You: File Sent\n");
//			}	
//    }

























































//  try{
//            //byte b[] = new byte[10000]; //reads 1kh byte data at a time from the file
//            byte b[] = new byte[10000] ;
//            int x=1 , j=0 ;
//            String s = "";
//         
//            
//          //  FileInputStream In = new FileInputStream(file) ;
//            
//            int read_bytes ;
//            
//            while( In.available()!=0)
//            {
//                System.out.println("avaialable data");
//                j=0 ;
//                s = "" ;
//                if( x <= 9) //for making parts name
//                {
//                    s = file+".00"+x ;
//                }
//                else
//                {
//                    s = file+".0"+x ;
//                }
//              
//               // Out = socket.getOutputStream() ;
//                
//                while( j<=50000 && In.available()!=0) //creates equal size file parts
//                {
//                    read_bytes = In.read(b, 0, 10000);
//                    System.out.println("\n" + read_bytes) ;
//                    
//                    j=j+read_bytes ; //to keep record of how much file it has read already
//                //if(JOptionPane.showConfirmDialog(ui, ("Accept ?")) == 0)
//                    Out.write(b, 0, read_bytes);
//                }
//                ui.jTextArea1.append("part"+x+"created") ;
//                x++ ;
//            }
//            System.out.println("file split2 successfully");
//         //   In.close();
//            
//            
//            ui.jTextArea1.append("[Applcation > Me] : File upload complete\n");
//            ui.jButton5.setEnabled(true); ui.jButton6.setEnabled(true);
//            ui.jTextField5.setVisible(true);
//            
//            if(In != null){ In.close(); }
//            if(Out != null){ Out.close(); }
//            if(socket != null){ socket.close(); }
//        }
//        catch (Exception ex) {
//            System.out.println("Exception [Upload : run()]");
//            ex.printStackTrace();
//        }
//    }

//}





























































//
//package com.socket;
//
//import com.ui.ChatFrame;
//import java.io.*;
//import java.net.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Upload implements Runnable{
//
//    public String addr;
//    public int port;
//    public Socket socket;
//    public FileInputStream In;
//    public OutputStream Out;
//    public File file;
//    public ChatFrame ui;
//    
//    public Upload(String addr, int port, File filepath, ChatFrame frame){
//        super();
//        try {
//            file = filepath; ui = frame;
//            socket = new Socket(InetAddress.getByName(addr), port);
//            Out = socket.getOutputStream();
//            In = new FileInputStream(filepath);
//        } 
//        catch (Exception ex) {
//            System.out.println("Exception [Upload : Upload(...)]");
//        }
//    }
//    
//    @Override
//    public void run() {
//        try {       
////            byte[] buffer = new byte[1024];
////            int count;
////            
////            while((count = In.read(buffer)) >= 0){
////                Out.write(buffer, 0, count);
////            }
////            Out.flush();
////            
////            ui.jTextArea1.append("[Applcation > Me] : File upload complete\n");
////            ui.jButton5.setEnabled(true); ui.jButton6.setEnabled(true);
////            ui.jTextField5.setVisible(true);
////            
//            
//                byte PART_SIZE = 5;
//    //    File inputFile = new File(FILE_NAME);
//
//    //    FileInputStream inputStream;
//
//        String newFileName;
//
//      //  FileOutputStream filePart;
//
//      
//        int fileSize = (int) file.length();
//
//        int nChunks = 0, read = 0, readLength = PART_SIZE;
//
//        byte[] byteChunkPart;
//
//       
//
//          //  In = new FileInputStream(file);
//
//            while (fileSize > 0) {
//
//                if (fileSize <= 5) {
//
//                    readLength = fileSize;
//
//                }
//
//                byteChunkPart = new byte[readLength];
//
//                read = In.read(byteChunkPart, 0, readLength);
//
//                fileSize -= read;
//
//                assert (read == byteChunkPart.length);
//
//                nChunks++;
//
//                newFileName = file + ".part"
//
//                        + Integer.toString(nChunks - 1);
//
//             //   filePart = new FileOutputStream(new File(newFileName));
//
//                Out.write(byteChunkPart);
//
//                Out.flush();
//
//          
//
//                byteChunkPart = null;
//
//                Out = null;
//
//            }
//
//            if(In != null){ In.close(); }
//            if(Out != null){ Out.close(); }
//            if(socket != null){ socket.close(); }
//        }
//        catch (Exception ex) {
//            System.out.println("Exception [Upload : run()]");
//            ex.printStackTrace();
//        }
//    }
//
//}























