package readinglist;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomeUserDetailService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfigurerAdapter.class);

	@Autowired
	private ReaderRepository readerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Load user by name {}", username);
		Optional<Reader> userDetails = readerRepository.findById(username);
		if (userDetails.isPresent()) {
			Reader reader = userDetails.get();
			logger.info("Get reader {}", reader.getUsername());
			return reader;
		}
		throw new UsernameNotFoundException("User '" + username + "' not found.");
	}

}
