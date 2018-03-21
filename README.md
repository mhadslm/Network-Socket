# digital-signature
generating a digital signature for a data file using public and private key:
1-keys generation
  In the development of this phase an instance of the abstract called KeyPairGenerator is constructed be calling the static method getInstance which must be initialized by a proper algorithm.
  RSA or DSA algorithms can be used, but in this implementation RSA was used.
  The created KeyPairGenerator object was used to create another object from another class called KeyPair from the security package java.security.
  The created KeyPair object was used to create the private and public key pair by calling getPrivate and getPublic respectively.
  
2-singing phase
  In the development of this phase the abstract called Signature from the security package java.security was used to create a digital signature.
  The hashing function “SHA512withRSA” was passed as a parameter to static method called getInstance to construct a new Signature object from that abstract.
  Then that object will be initialized with the private key by calling its method initSign to use it to encrypt the generated digest as illustrated in the previous sections.
  The created object will be updated with data that will be signed by calling the method update. The data will be in an array of bytes. In the last step the method called sign will be called to sign the data.
  The sign method returns the signed data in an array of bytes to facilitate writing these bytes on disk like a signature.
  
3-verification phase
  This phase is completely identical in the practical part with the previous part.
  The same object Signature was constructed but it must be initialized with the public key to use it decrypt the original digest by calling initVerify method.
  The created object will be updated by calling the method update with the original signature after reading it from disk and store it an array of bytes.
  These bytes will be verified In the last step the method called verify will be called to verify the signature and return true if the signature was valid or false otherwise.
  
