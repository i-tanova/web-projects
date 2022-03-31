package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialsService {

    private CredentialsMapper mapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper mapper, EncryptionService encryptionService) {
        this.mapper = mapper;
        this.encryptionService = encryptionService;
    }

    public void createCredential(Credential credential) {
        String encodedSalt = generateKey();
        credential.setKey(encodedSalt);
        String encriptedPassword = encryptionService.encryptValue(credential.getUnencodedPassword(), encodedSalt);
        credential.setPassword(encriptedPassword);

        this.mapper.insert(credential);
    }

    private String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public void updateCredential(Credential credential) {
        System.out.println("updateCredential " + credential.getCredentialid());
        String encodedSalt = generateKey();
        credential.setKey(encodedSalt);
        String encriptedPassword = encryptionService.encryptValue(credential.getUnencodedPassword(), encodedSalt);
        credential.setPassword(encriptedPassword);

        this.mapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialId) {
        this.mapper.deleteCredential(credentialId);
    }

    public List<Credential> getUserCredentials(int userId) {
        List<Credential> credentialList = this.mapper.getCredentials(userId);
        return credentialList.stream().map(c -> wrapCredential(c)).collect(Collectors.toList());
    }

    private Credential wrapCredential(Credential c) {
        Credential mapped =  new Credential(c.getCredentialid(), c.getUrl(), c.getUsername(),
                null, c.getPassword(), c.getUserid());
        mapped.setUnencodedPassword(getUnencodedPassword(c));
        return mapped;
    }

    private String getUnencodedPassword(Credential credential) {
        return this.encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }


    public Credential getCredentialById(int credentialId) {
        return wrapCredential(this.mapper.getCredentialById(credentialId));
    }

}
