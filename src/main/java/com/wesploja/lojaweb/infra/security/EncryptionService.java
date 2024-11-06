package com.wesploja.lojaweb.infra.security;

import com.wesploja.lojaweb.infra.exception.TratarEncrypError;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionService {
    private static final String ALGORITHM = "AES";
    private static final String FIXED_KEY = "12345678901234567890123456789012"; // 32 bytes para AES-256
    private SecretKey secretKey;

    public EncryptionService() {
        try {
            byte[] decodedKey = FIXED_KEY.getBytes(); // usa a chave de 32 bytes diretamente
            secretKey = new SecretKeySpec(decodedKey, ALGORITHM);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Erro ao inicializar a chave de criptografia", e);
        }
    }

    public String encrypt(String data){
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){
            throw new TratarEncrypError("Erro ao criptografar os dados");
        }


    }

    public String decrypt(String encryptedData){
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        }catch (Exception ex){
            throw new TratarEncrypError("Erro ao descriptografar os dados");
        }
    }
}




