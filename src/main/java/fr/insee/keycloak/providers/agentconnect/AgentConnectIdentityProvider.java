package fr.insee.keycloak.providers.agentconnect;

import fr.insee.keycloak.providers.common.AbstractBaseIdentityProvider;
import fr.insee.keycloak.providers.common.Utils;
import org.keycloak.OAuth2Constants;
import org.keycloak.broker.provider.AuthenticationRequest;
import org.keycloak.models.KeycloakSession;

import javax.ws.rs.core.UriBuilder;

public class AgentConnectIdentityProvider extends AbstractBaseIdentityProvider<AgentConnectIdentityProviderConfig> {

  public AgentConnectIdentityProvider(KeycloakSession session, AgentConnectIdentityProviderConfig config) {
    super(session, config, Utils.getJsonWebKeySetFrom(config.getJwksUrl(), session));
  }

  @Override
  protected UriBuilder createAuthorizationUrl(AuthenticationRequest request) {

    var config = getConfig();

    request.getAuthenticationSession().setClientNote(OAuth2Constants.ACR_VALUES, config.getEidasLevel().toString());
    var uriBuilder = super.createAuthorizationUrl(request);

    logger.debugv("AgentConnect Authorization Url: {0}", uriBuilder.build().toString());

    return uriBuilder;
  }
}