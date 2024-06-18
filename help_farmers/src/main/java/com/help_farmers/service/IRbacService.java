package com.help_farmers.service;

import java.util.List;

public interface IRbacService {

    String findRolesByUserName(String userName);

    List<String> findAuthoritiesByRoleName(String roleName);

    List<String> findActionsByRoleName(String roleName);
}
