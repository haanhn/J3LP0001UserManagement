/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.utils;

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

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
//            String text = sc.nextLine().trim();
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(text.getBytes());
//            MessageDigest digest2 = MessageDigest.getInstance("SHA-256");
//            byte[] h = digest2.digest(new String("haanh").getBytes());
//            for (byte b : hash) {
//                System.out.print(b);
//                System.out.println("");
//            }
//            for (byte b : h) {
//                System.out.print(b);
//                System.out.println("");
//            }
//            System.out.println(Arrays.equals(h, hash));

            while (true) {

                String email = sc.nextLine().trim();
                boolean valid;
//                String regex = "\\w{5,30}@\\w{2,20}(.\\w{2,20}){1,2}";
                String regex = "[a-zA-Z0-9]{5,30}@[a-zA-Z0-9]{1,20}(\\.[a-zA-Z0-9]{1,20}){1,2}";
//                String regex = "[0-9]{9,12}";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(email);
                valid = matcher.matches();
                System.out.println("haanh.utils.StringUtils.main(): " + valid);
//        return valid;
            }
        } catch (Exception ex) {
            Logger.getLogger(StringUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
