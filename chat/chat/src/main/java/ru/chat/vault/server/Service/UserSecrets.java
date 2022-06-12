package ru.chat.vault.server.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import ru.chat.model.Credentials;
import ru.chat.model.USecret;


@Service
public class UserSecrets {

    @Autowired
    VaultTemplate vaultTemplate;

    public void setUsersSecrets(String path, USecret credentials) {
        vaultTemplate.write(path,credentials);
    }

    public VaultResponseSupport<Credentials> ReadSecret(String secretPath){
        return vaultTemplate
                .read("transit/chat", Credentials.class);
    }
}
