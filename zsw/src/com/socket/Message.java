package com.socket;

import java.io.Serializable;

public class Message implements Serializable{
    
    private static final long serialVersionUID = 1L;
    public String type, sender, content, recipient , encContent , plainContent , content1 , content2 ;
//    
//    public Message(String type, String sender, String encContent, String plainContent, String recipient){
//        this.type = type; this.sender = sender; this.encContent = encContent; this.plainContent = plainContent ; this.recipient = recipient;
//    }
   
   public String data ,  sender1 , receiver , type1 ;
   public byte[] k;
   
   
    public byte[] pubKey1;
    public String recipient1;
    public String encKey;
   

    public Message( String sender1 , String receiver , String data , byte[] k )
    {
        this.data=data;
        this.k = k;
        this.sender1 = sender1 ;
        this.receiver = receiver ;
//        if(iv==null)
//        {
//           this.iv = null;
//        }
//        else
//        {
//            this.iv = new byte[iv.length];
//            for(int i=0;i<iv.length;i++)
//            {
//             this.iv[i]=iv[i];
//            }
//        }
    }
     public Message(String type, String sender, String content, String recipient , byte[] k){
        this.type = type; this.sender = sender; this.content = content; this.recipient = recipient; this.k = k ;
    } 
    
    public Message( String type , String sender , String content , String recipient , String type1){
        this.type = type; this.sender = sender; this.content = content ; this.recipient = recipient; this.type1 = type1 ;
    }
    
    public Message(String type, String sender, String content, String recipient){
        this.type = type; this.sender = sender; this.content = content; this.recipient = recipient;
    }
    
    
    
    
    public Message(String type, String sender1, byte[] pubKey1, String content1,String recipient1){
        this.type = type; this.sender1 = sender1; this.pubKey1 = pubKey1; this.content1 = content1; this.recipient1 = recipient1;
    }
    
    public Message( String type , String sender , String content ,String enckey , String recipient , String type1){
        this.type = type; this.sender = sender; this.content = content ; this.encKey = enckey ; this.recipient = recipient; this.type1 = type1 ;
    }
        
    public Message( String type , String sender1 , String recipient1 ){
       this.type = type ; this.sender1 = sender1 ; this.recipient1 = recipient1 ;
    }
        
    @Override
    public String toString(){
        return "{type='"+type+"', sender='"+sender+"', content='"+content+"', recipient='"+recipient+"'}";
    }
}
