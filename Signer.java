package ds;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class Signer {

    private final String algorithm = "DSA";
    private final String provider = "SUN";
    private FileOutputStream fos;
    private FileInputStream fis;
    private BufferedInputStream bis;

    public Signer() {

    }

    public boolean sign(File msgFile, File pvkFile, File sigFile) {
        try {
            
            //read the private key 
            fis = new FileInputStream(pvkFile);
            byte[] encodedPrivateKey = new byte[(int) pvkFile.length()];
            fis.read(encodedPrivateKey);
            fis.close();
            
            //decrypt the key
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm, provider);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            Signature dsa = Signature.getInstance(algorithm);
            
            //initialize the  Signature with the private key
            dsa.initSign(privateKey);
            
            //read the message from the file
            fis = new FileInputStream(msgFile);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = bis.read(buffer)) >= 0) {
                dsa.update(buffer, 0, length);
            }
            
            //sign the message with the signature
            byte[] signature = dsa.sign();

            //writr the signature to a destination file
            fos = new FileOutputStream(sigFile);
            fos.write(signature);
            fos.close();
            System.out.println(signature.toString());
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
