package com.example.services.security.authentication;

import com.example.models.security.EmployeeUserDetails;
import com.nimbusds.jwt.JWTClaimsSet;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.config.TokenConfiguration;
import io.micronaut.security.token.jwt.generator.claims.ClaimsAudienceProvider;
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator;
import io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator;

import javax.inject.Singleton;
import java.util.Map;

@Singleton
@Replaces(bean = JWTClaimsSetGenerator.class)
public class CustomJWTClaimsGenerator extends JWTClaimsSetGenerator {
  /**
   * @param tokenConfiguration       Token Configuration
   * @param jwtIdGenerator           Generator which creates unique JWT ID
   * @param claimsAudienceProvider   Provider which identifies the recipients that the JWT is intended for.
   * @param applicationConfiguration The application configuration
   */
  public CustomJWTClaimsGenerator(TokenConfiguration tokenConfiguration,
                                  @Nullable JwtIdGenerator jwtIdGenerator,
                                  @Nullable ClaimsAudienceProvider claimsAudienceProvider,
                                  @Nullable ApplicationConfiguration applicationConfiguration) {
    super(tokenConfiguration, jwtIdGenerator, claimsAudienceProvider, applicationConfiguration);
  }

  /**
   * @param tokenConfiguration     Token Configuration
   * @param jwtIdGenerator         Generator which creates unique JWT ID
   * @param claimsAudienceProvider Provider which identifies the recipients that the JWT is intented for.
   */
  public CustomJWTClaimsGenerator(TokenConfiguration tokenConfiguration,
                                  @Nullable JwtIdGenerator jwtIdGenerator,
                                  @Nullable ClaimsAudienceProvider claimsAudienceProvider) {
    super(tokenConfiguration, jwtIdGenerator, claimsAudienceProvider);
  }

  @Override
  protected void populateWithUserDetails(JWTClaimsSet.Builder builder, UserDetails userDetails) {
    super.populateWithUserDetails(builder, userDetails);
    if (userDetails instanceof EmployeeUserDetails) {
      for (Map.Entry<String, Object> entry: ((EmployeeUserDetails) userDetails).getClaims().entrySet()) {
        builder.claim(entry.getKey(), entry.getValue());
      }
    }
  }
}
