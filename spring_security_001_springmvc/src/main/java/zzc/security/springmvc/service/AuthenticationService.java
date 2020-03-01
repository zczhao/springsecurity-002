package zzc.security.springmvc.service;

import zzc.security.springmvc.model.AuthenticationRequest;
import zzc.security.springmvc.model.UserDTO;

public interface AuthenticationService {

	UserDTO authentication(AuthenticationRequest authenticationRequest);
}
