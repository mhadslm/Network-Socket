package ds;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class Verifier {
    private final String algorithm = "DSA";
    private final String provider = "SUN";
    
    private FileInputStream fis;
    private BufferedInputStream bis;
    private byte[] data, signature;

    public Verifier() {

    }

    public boolean verify(File m, File k, File s) {
        try {
            //read the encrypted public key
            /*fis = new FileInputStream(k);
             data = new byte[fis.available()];
             fis.read(data);
             fis.close();

             //decrypt the publick key using the same algorithm used in the encryption stage
            
             X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(data);
             KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
             PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);*/

            // Read Public Key.
            FileInputStream fis = new FileInputStream(k);
            byte[] encodedPublicKey = new byte[(int) k.length()];
            fis.read(encodedPublicKey);
            fis.close();
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm, provider);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            //read the signature from the file
            fis = new FileInputStream(s);
            signature = new byte[fis.available()];
            fis.read(signature);
            fis.close();

            //create signature object
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(publicKey);

            //read the message
            fis = new FileInputStream(m);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bis.available() != 0) {
                len = bis.read(buffer);
                sig.update(buffer, 0, len);
            }
            bis.close();

            //the verification
            return sig.verify(signature);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
