package ru.dolinini.notebook.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dolinini.notebook.model.User;
import ru.dolinini.notebook.repos.UserRepo;

@Service("userdetailsserviceimpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String firstname) throws UsernameNotFoundException {

        User user=userRepo.findByFirstname(firstname).orElseThrow(()->
                new UsernameNotFoundException("User with such name doesn't exists")
        );
        return SecurityUser.getUserDetailsFromUser(user);
    }
}
