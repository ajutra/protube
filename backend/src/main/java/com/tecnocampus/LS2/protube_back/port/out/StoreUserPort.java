package com.tecnocampus.LS2.protube_back.port.out;

import com.tecnocampus.LS2.protube_back.domain.model.User;

public interface StoreUserPort {
    void storeUser(User user);
    void checkIfUserExists(String username);
}
