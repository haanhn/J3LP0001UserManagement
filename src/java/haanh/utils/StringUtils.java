/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author HaAnh
 */
public class StringUtils {
    
    private static char[] BYTE_TO_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static void main(String[] args) {
        try {
//            Scanner sc = new Scanner(System.in);
//            String text = sc.nextLine().trim();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            MessageDigest digest2 = MessageDigest.getInstance("SHA-256");
            
            byte[] h = new String("haanh" ).getBytes();
            byte[] h2 = new String("haanh" ).getBytes();
            h = digest.digest(h);
            h2 = digest2.digest(h2);
            System.out.println(Base64.getEncoder().encodeToString(h));
            System.out.println(Base64.getEncoder().encodeToString(h2));
            
//            byte[] hashedBytes = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
//            for (byte b : hashedBytes) {
//                int unsignedByte = b & 0xFF;
//                int leftBits = unsignedByte >>> 4;
//                int rightBits = unsignedByte & 0x0F;
//                System.out.println(leftBits);
//                System.out.println(rightBits);
//                // 0100 1101
//                // 0100 1101 >> 4 = 0000 0100
//                // 0100 1101 & 0000 1111 = 1101 0000 
//                sb.append(BYTE_TO_HEX[leftBits]).append(BYTE_TO_HEX[rightBits]);
//            }
            String hashedString = sb.toString();
            
//            System.out.println(Base64.getEncoder().encodeToString(hashedBytes));
            
//            System.out.println(hashedString);
//            
//            String encoded = Base64.getEncoder().encodeToString(hash);
//            System.out.println(big.toString(16));
//            System.out.println(big2.toString(16));
//            System.out.println(Arrays.equals(big.toByteArray(), big2.toByteArray()));
            
//            String s = new String
//            System.out.println(digest.to);
//            System.out.println(new String(hash));
//            new Byte
//            for (byte b : hash) {
//                System.out.print(b);
//                System.out.println("");
//            }
//            for (byte b : h) {
//                System.out.print(b);
//                System.out.println("");
//            }
//            System.out.println(Arrays.equals(h, hash));

//            while (true) {
//
//                String email = sc.nextLine().trim();
//                boolean valid;
////                String regex = "\\w{5,30}@\\w{2,20}(.\\w{2,20}){1,2}";
//                String regex = "[a-zA-Z0-9]{5,30}@[a-zA-Z0-9]{1,20}(\\.[a-zA-Z0-9]{1,20}){1,2}";
////                String regex = "[0-9]{9,12}";
//                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//                Matcher matcher = pattern.matcher(email);
//                valid = matcher.matches();
//                System.out.println("haanh.utils.StringUtils.main(): " + valid);
////        return valid;
//            }
        } catch (Exception ex) {
            Logger.getLogger(StringUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
