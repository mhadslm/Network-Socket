/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author Muhammad
 */
public class KeysGenerator {
    
    private final String algorithm = "DSA";
    private final String provider = "SUN";
    private FileOutputStream fos;
    
    
    public KeysGenerator(){
        
    }
    
        public boolean generateKeys(File pbkFile, File pvKFile) {
            
            try{
            KeyPairGenerator kg = KeyPairGenerator.getInstance(algorithm, provider);

            SecureRandom sha1 = SecureRandom.getInstance("SHA1PRNG", "SUN");
            //SecureRandom rsa = SecureRandom.getInstanceStrong();

            kg.initialize(1024, sha1);

            //KeyPair keyPair = kg.genKeyPair();
            KeyPair pair = kg.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            
            //write the encoded publick key to a file
            // Store Public Key.
            X509EncodedKeySpec x509EncodedPublicKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            fos = new FileOutputStream(pbkFile);
            fos.write(x509EncodedPublicKeySpec.getEncoded());
            fos.close();
            
            //write the encoded private key to a file
            // Store private Key.
            PKCS8EncodedKeySpec pkcs8EncodedPrivatrKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            fos = new FileOutputStream(pvKFile);
            fos.write(pkcs8EncodedPrivatrKeySpec.getEncoded());
            fos.close();

    
            return true;
            
            
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
            
        }

    
}
