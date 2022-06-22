package com.neepa.config.shiro;


import com.neepa.utils.Constants;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class UserModularRealmAuthorizer extends ModularRealmAuthorizer {
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        HashMap<String, Realm> realmHashMap = new HashMap<>(realms.size());
        for (Realm realm : realms) {
            if (realm.getName().contains(Constants.UserType.STAFF.type)) {
                realmHashMap.put("StaffRealm", realm);
            } else if (realm.getName().contains(Constants.UserType.STUDENT.type)) {
                realmHashMap.put("StudentRealm", realm);
            }
        }

        Set<String> realmNames = principals.getRealmNames();
        if (realmNames != null) {
            String realmName = null;
            Iterator it = realmNames.iterator();
            while (it.hasNext()) {
                realmName = ConvertUtils.convert(it.next());
                if (realmName.contains(Constants.UserType.STAFF.type)) {
                    return ((StaffRealm) realmHashMap.get("StaffRealm")).isPermitted(principals, permission);
                } else if (realmName.contains(Constants.UserType.STUDENT.type)) {
                    return ((StudentRealm) realmHashMap.get("StudentRealm")).isPermitted(principals, permission);
                }
                break;
            }
        }
        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        HashMap<String, Realm> realmHashMap = new HashMap<>(realms.size());
        for (Realm realm : realms) {
            if (realm.getName().contains(Constants.UserType.STAFF.type)) {
                realmHashMap.put("StaffRealm", realm);
            } else if (realm.getName().contains(Constants.UserType.STUDENT.type)) {
                realmHashMap.put("StudentRealm", realm);
            }
        }

        Set<String> realmNames = principals.getRealmNames();
        if (realmNames != null) {
            String realmName = null;
            Iterator it = realmNames.iterator();
            while (it.hasNext()) {
                realmName = ConvertUtils.convert(it.next());
                if (realmName.contains(Constants.UserType.STAFF.type)) {
                    return ((StaffRealm) realmHashMap.get("StaffRealm")).hasRole(principals, roleIdentifier);
                } else if (realmName.contains(Constants.UserType.STUDENT.type)) {
                    return ((StudentRealm) realmHashMap.get("StudentRealm")).hasRole(principals, roleIdentifier);
                }
                break;
            }
        }
        return false;
    }
}