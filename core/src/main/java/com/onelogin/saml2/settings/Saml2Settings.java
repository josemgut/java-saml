package com.onelogin.saml2.settings;

import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.onelogin.saml2.model.hsm.HSM;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.onelogin.saml2.model.Contact;
import com.onelogin.saml2.model.Organization;
import com.onelogin.saml2.util.Constants;
import com.onelogin.saml2.util.SchemaFactory;
import com.onelogin.saml2.util.Util;

/**
 * Saml2Settings class of OneLogin's Java Toolkit.
 *
 * A class that implements the settings handler
 */ 
public class Saml2Settings {
	/**
     * Private property to construct a logger for this class.
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(Saml2Settings.class);

	// Toolkit settings
	private boolean strict = true;
	private boolean debug = false;
	
	// SP
	private String spEntityId = "";
	private URL spAssertionConsumerServiceUrl = null;
	private String spAssertionConsumerServiceBinding = Constants.BINDING_HTTP_POST;
	private URL spSingleLogoutServiceUrl = null;
	private String spSingleLogoutServiceBinding = Constants.BINDING_HTTP_REDIRECT;
	private String spNameIDFormat = Constants.NAMEID_UNSPECIFIED;
	private X509Certificate spX509cert = null;
	private X509Certificate spX509certNew = null;
	private PrivateKey spPrivateKey = null;
	private HSM hsm = null;

	// IdP
	private String idpEntityId = "";
	private URL idpSingleSignOnServiceUrl = null;
	private String idpSingleSignOnServiceBinding = Constants.BINDING_HTTP_REDIRECT;
	private URL idpSingleLogoutServiceUrl = null;
	private URL idpSingleLogoutServiceResponseUrl = null;
	private String idpSingleLogoutServiceBinding = Constants.BINDING_HTTP_REDIRECT;
	private X509Certificate idpx509cert = null;
	private List<X509Certificate> idpx509certMulti = null;
	private String idpCertFingerprint = null;
	private String idpCertFingerprintAlgorithm = "sha1";

	// Security
	private boolean nameIdEncrypted = false;
	private boolean authnRequestsSigned = false;
	private boolean logoutRequestSigned = false;
	private boolean logoutResponseSigned = false;
	private boolean wantMessagesSigned = false;
	private boolean wantAssertionsSigned = false;
	private boolean wantAssertionsEncrypted = false;
	private boolean wantNameId = true;
	private boolean wantNameIdEncrypted = false;
	private boolean signMetadata = false;
	private List<String> requestedAuthnContext = new ArrayList<>();
	private String requestedAuthnContextComparison = "exact";
	private boolean wantXMLValidation = true;
	private String signatureAlgorithm = Constants.RSA_SHA1;
	private String digestAlgorithm = Constants.SHA1;
	private boolean rejectUnsolicitedResponsesWithInResponseTo = false;
	private boolean allowRepeatAttributeName = false;
	private boolean rejectDeprecatedAlg = false;
	private String uniqueIDPrefix = null;

	// Compress
	private boolean compressRequest = true;
	private boolean compressResponse = true;
	
	// Parsing
	private boolean trimNameIds = false;
	private boolean trimAttributeValues = false;

	// Misc
	private List<Contact> contacts = new LinkedList<>();
	private Organization organization = null;

	private boolean spValidationOnly = false;
	
	/**
	 * @return the strict setting value
	 */
	public final boolean isStrict() {
		return strict;
	}

	/**
	 * @return the spEntityId setting value
	 */
	public final String getSpEntityId() {
		return spEntityId;
	}

	/**
	 * @return the spAssertionConsumerServiceUrl
	 */
	public final URL getSpAssertionConsumerServiceUrl() {
		return spAssertionConsumerServiceUrl;
	}

	/**
	 * @return the spAssertionConsumerServiceBinding setting value
	 */
	public final String getSpAssertionConsumerServiceBinding() {
		return spAssertionConsumerServiceBinding;
	}

	/**
	 * @return the spSingleLogoutServiceUrl setting value
	 */
	public final URL getSpSingleLogoutServiceUrl() {
		return spSingleLogoutServiceUrl;
	}

	/**
	 * @return the spSingleLogoutServiceBinding setting value
	 */
	public final String getSpSingleLogoutServiceBinding() {
		return spSingleLogoutServiceBinding;
	}

	/**
	 * @return the spNameIDFormat setting value
	 */
	public final String getSpNameIDFormat() {
		return spNameIDFormat;
	}

	/**
	 * @return the allowRepeatAttributeName setting value
	 */
	public boolean isAllowRepeatAttributeName() {
		return allowRepeatAttributeName;
	}

	/**
	 * @return the rejectDeprecatedAlg setting value
	 */
	public boolean getRejectDeprecatedAlg() {
		return rejectDeprecatedAlg;
	}

	/**
	 * @return the spX509cert setting value
	 */
	public final X509Certificate getSPcert() {
		return spX509cert;
	}

	/**
	 * @return the spX509certNew setting value
	 */
	public final X509Certificate getSPcertNew() {
		return spX509certNew;
	}

	/**
	 * @return the spPrivateKey setting value
	 */
	public final PrivateKey getSPkey() {
		return spPrivateKey;
	}

	/**
	 * @return the idpEntityId setting value
	 */
	public final String getIdpEntityId() {
		return idpEntityId;
	}

	/**
	 * @return the idpSingleSignOnServiceUrl setting value
	 */
	public final URL getIdpSingleSignOnServiceUrl() {
		return idpSingleSignOnServiceUrl;
	}

	/**
	 * @return the idpSingleSignOnServiceBinding setting value
	 */
	public final String getIdpSingleSignOnServiceBinding() {
		return idpSingleSignOnServiceBinding;
	}

	/**
	 * @return the idpSingleLogoutServiceUrl setting value
	 */
	public final URL getIdpSingleLogoutServiceUrl() {
		return idpSingleLogoutServiceUrl;
	}

	/**
	 * @return the idpSingleLogoutServiceResponseUrl setting value
	 */
	public final URL getIdpSingleLogoutServiceResponseUrl() {
		if (idpSingleLogoutServiceResponseUrl == null) {
			return getIdpSingleLogoutServiceUrl();
		}

		return idpSingleLogoutServiceResponseUrl;
	}

	/**
	 * @return the idpSingleLogoutServiceBinding setting value
	 */
	public final String getIdpSingleLogoutServiceBinding() {
		return idpSingleLogoutServiceBinding;
	}

	/**
	 * @return the idpx509cert setting value
	 */
	public final X509Certificate getIdpx509cert() {
		return idpx509cert;
	}

	/**
	 * @return the idpCertFingerprint setting value
	 */
	public final String getIdpCertFingerprint() {
		return idpCertFingerprint;
	}

	/**
	 * @return the idpCertFingerprintAlgorithm setting value
	 */
	public final String getIdpCertFingerprintAlgorithm() {
		return idpCertFingerprintAlgorithm;
	}

	/**
	 * @return the idpx509certMulti setting value
	 */
	public List<X509Certificate> getIdpx509certMulti() {
		return idpx509certMulti;
	}

	/**
	 * @return the nameIdEncrypted setting value
	 */
	public boolean getNameIdEncrypted() {
		return nameIdEncrypted;
	}

	/**
	 * @return the authnRequestsSigned setting value
	 */
	public boolean getAuthnRequestsSigned() {
		return authnRequestsSigned;
	}

	/**
	 * @return the logoutRequestSigned setting value
	 */
	public boolean getLogoutRequestSigned() {
		return logoutRequestSigned;
	}

	/**
	 * @return the logoutResponseSigned setting value
	 */
	public boolean getLogoutResponseSigned() {
		return logoutResponseSigned;
	}

	/**
	 * @return the wantMessagesSigned setting value
	 */
	public boolean getWantMessagesSigned() {
		return wantMessagesSigned;
	}

	/**
	 * @return the wantAssertionsSigned setting value
	 */
	public boolean getWantAssertionsSigned() {
		return wantAssertionsSigned;
	}

	/**
	 * @return the wantAssertionsEncrypted setting value
	 */
	public boolean getWantAssertionsEncrypted() {
		return wantAssertionsEncrypted;
	}

	/**
	 * @return the wantNameId setting value
	 */
	public boolean getWantNameId() {
		return wantNameId;
	}
	
	/**
	 * @return the wantNameIdEncrypted setting value
	 */
	public boolean getWantNameIdEncrypted() {
		return wantNameIdEncrypted;
	}

	/**
	 * @return the signMetadata setting value
	 */
	public boolean getSignMetadata() {
		return signMetadata;
	}

	/**
	 * @return the requestedAuthnContext setting value
	 */
	public List<String> getRequestedAuthnContext() {
		return requestedAuthnContext;
	}

	/**
	 * @return the requestedAuthnContextComparison setting value
	 */
	public String getRequestedAuthnContextComparison() {
		return requestedAuthnContextComparison;
	}

	/**
	 * @return the wantXMLValidation setting value
	 */
	public boolean getWantXMLValidation() {
		return wantXMLValidation;
	}

	/**
	 * @return the signatureAlgorithm setting value
	 */
	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	/**
	 * @return the digestAlgorithm setting value
	 */
	public String getDigestAlgorithm() {
		return digestAlgorithm;
	}

	/**
	 * @return SP Contact info
	 */
	public List<Contact> getContacts() {
		return this.contacts;
	}

	/**
	 * @return SP Organization info
	 */
	public Organization getOrganization() {
		return this.organization;
	}

	/**
	 * @return Unique ID prefix
	 */
	public String getUniqueIDPrefix() {
		return this.uniqueIDPrefix;
	}

	/**
	 * @return The HSM setting value.
	 */
	public HSM getHsm() {
		return this.hsm;
	}

	/**
	 * @return if the debug is active or not
	 */
	public boolean isDebugActive() {
		return this.debug;
	}
	
	/**
	 * Set the strict setting value
	 * 
	 * @param strict
	 *            the strict to be set
	 */
	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	/**
	 * Set the debug setting value
	 *
	 * @param debug
	 *            the debug mode to be set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Sets the HSM setting value.
	 *
	 * @param hsm The HSM object to be set.
	 */
	public void setHsm(HSM hsm) {
		this.hsm = hsm;
	}

	/**
	 * Set the spEntityId setting value
	 *
	 * @param spEntityId
	 *            the spEntityId value to be set
	 */
	protected final void setSpEntityId(String spEntityId) {
		this.spEntityId = spEntityId;
	}

	/**
	 * Set the spAssertionConsumerServiceUrl setting value
	 *
	 * @param spAssertionConsumerServiceUrl
	 *            the spAssertionConsumerServiceUrl value to be set
	 */
	protected final void setSpAssertionConsumerServiceUrl(URL spAssertionConsumerServiceUrl) {
		this.spAssertionConsumerServiceUrl = spAssertionConsumerServiceUrl;
	}

	/**
	 * Set the spAssertionConsumerServiceBinding setting value
	 *
	 * @param spAssertionConsumerServiceBinding
	 *            the spAssertionConsumerServiceBinding value to be set
	 */
	protected final void setSpAssertionConsumerServiceBinding(String spAssertionConsumerServiceBinding) {
		this.spAssertionConsumerServiceBinding = spAssertionConsumerServiceBinding;
	}

	/**
	 * Set the spSingleLogoutServiceUrl setting value
	 *
	 * @param spSingleLogoutServiceUrl
	 *            the spSingleLogoutServiceUrl value to be set
	 */
	protected final void setSpSingleLogoutServiceUrl(URL spSingleLogoutServiceUrl) {
		this.spSingleLogoutServiceUrl = spSingleLogoutServiceUrl;
	}

	/**
	 * Set the spSingleLogoutServiceBinding setting value
	 *
	 * @param spSingleLogoutServiceBinding
	 *            the spSingleLogoutServiceBinding value to be set
	 */
	protected final void setSpSingleLogoutServiceBinding(String spSingleLogoutServiceBinding) {
		this.spSingleLogoutServiceBinding = spSingleLogoutServiceBinding;
	}

	/**
	 * Set the spNameIDFormat setting value
	 *
	 * @param spNameIDFormat
	 *            the spNameIDFormat value to be set
	 */
	protected final void setSpNameIDFormat(String spNameIDFormat) {
		this.spNameIDFormat = spNameIDFormat;
	}

	/**
	 * Set the allowRepeatAttributeName setting value
	 *
	 * @param allowRepeatAttributeName
	 *        the allowRepeatAttributeName value to be set
	 */
	public void setAllowRepeatAttributeName (boolean allowRepeatAttributeName) {
		this.allowRepeatAttributeName = allowRepeatAttributeName;
	}

	/**
	 * Set the rejectDeprecatedAlg setting value
	 *
	 * @param rejectDeprecatedAlg
	 *        the rejectDeprecatedAlg value to be set
	 */
	public void setRejectDeprecatedAlg (boolean rejectDeprecatedAlg) {
		this.rejectDeprecatedAlg = rejectDeprecatedAlg;
	}

	/**
	 * Set the spX509cert setting value provided as X509Certificate object
	 *
	 * @param spX509cert
	 *            the spX509cert value to be set in X509Certificate format
	 */
	protected final void setSpX509cert(X509Certificate spX509cert) {
		this.spX509cert = spX509cert;
	}

	/**
	 * Set the spX509certNew setting value provided as X509Certificate object
	 *
	 * @param spX509certNew
	 *            the spX509certNew value to be set in X509Certificate format
	 */
	protected final void setSpX509certNew(X509Certificate spX509certNew) {
		this.spX509certNew = spX509certNew;
	}

	/**
	 * Set the spPrivateKey setting value provided as a PrivateKey object
	 *
	 * @param spPrivateKey
	 *            the spprivateKey value to be set in PrivateKey format
	 */
	protected final void setSpPrivateKey(PrivateKey spPrivateKey) {
		this.spPrivateKey = spPrivateKey;
	}

	/**
	 * Set the uniqueIDPrefix setting value
	 *
	 * @param uniqueIDPrefix
	 *            the Unique ID prefix used when generating Unique ID
	 */
	protected final void setUniqueIDPrefix(String uniqueIDPrefix) {
		this.uniqueIDPrefix = uniqueIDPrefix;
	}

	/**
	 * Set the idpEntityId setting value
	 *
	 * @param idpEntityId
	 *            the idpEntityId value to be set
	 */
	protected final void setIdpEntityId(String idpEntityId) {
		this.idpEntityId = idpEntityId;
	}

	/**
	 * Set the idpSingleSignOnServiceUrl setting value
	 *
	 * @param idpSingleSignOnServiceUrl
	 *            the idpSingleSignOnServiceUrl value to be set
	 */
	protected final void setIdpSingleSignOnServiceUrl(URL idpSingleSignOnServiceUrl) {
		this.idpSingleSignOnServiceUrl = idpSingleSignOnServiceUrl;
	}

	/**
	 * Set the idpSingleSignOnServiceBinding setting value
	 *
	 * @param idpSingleSignOnServiceBinding
	 *            the idpSingleSignOnServiceBinding value to be set
	 */
	protected final void setIdpSingleSignOnServiceBinding(String idpSingleSignOnServiceBinding) {
		this.idpSingleSignOnServiceBinding = idpSingleSignOnServiceBinding;
	}

	/**
	 * Set the idpSingleLogoutServiceUrl setting value
	 *
	 * @param idpSingleLogoutServiceUrl
	 *            the idpSingleLogoutServiceUrl value to be set
	 */
	protected final void setIdpSingleLogoutServiceUrl(URL idpSingleLogoutServiceUrl) {
		this.idpSingleLogoutServiceUrl = idpSingleLogoutServiceUrl;
	}

	/**
	 * Set the idpSingleLogoutServiceUrl setting value
	 *
	 * @param idpSingleLogoutServiceResponseUrl
	 *            the idpSingleLogoutServiceUrl value to be set
	 */
	protected final void setIdpSingleLogoutServiceResponseUrl(URL idpSingleLogoutServiceResponseUrl) {
			this.idpSingleLogoutServiceResponseUrl = idpSingleLogoutServiceResponseUrl;
	}


	/**
	 * Set the idpSingleLogoutServiceBinding setting value
	 *
	 * @param idpSingleLogoutServiceBinding
	 *            the idpSingleLogoutServiceBinding value to be set
	 */
	protected final void setIdpSingleLogoutServiceBinding(String idpSingleLogoutServiceBinding) {
		this.idpSingleLogoutServiceBinding = idpSingleLogoutServiceBinding;
	}

	/**
	 * Set the idpX509cert setting value provided as a X509Certificate object
	 *
	 * @param idpX509cert
	 *            the idpX509cert value to be set in X509Certificate format
	 */
	protected final void setIdpx509cert(X509Certificate idpX509cert) {
		this.idpx509cert = idpX509cert;
	}

	/**
	 * Set the idpCertFingerprint setting value
	 *
	 * @param idpCertFingerprint
	 *            the idpCertFingerprint value to be set
	 */
	protected final void setIdpCertFingerprint(String idpCertFingerprint) {
		this.idpCertFingerprint = idpCertFingerprint;
	}

	/**
	 * Set the idpCertFingerprintAlgorithm setting value
	 *
	 * @param idpCertFingerprintAlgorithm
	 *            the idpCertFingerprintAlgorithm value to be set.
	 */
	protected final void setIdpCertFingerprintAlgorithm(String idpCertFingerprintAlgorithm) {
		this.idpCertFingerprintAlgorithm = idpCertFingerprintAlgorithm;
	}

	/**
	 * Set the idpx509certMulti setting value
	 *
	 * @param idpx509certMulti the idpx509certMulti to set
	 */
	public void setIdpx509certMulti(List<X509Certificate> idpx509certMulti) {
		this.idpx509certMulti= idpx509certMulti;
	}

	/**
	 * Set the nameIdEncrypted setting value
	 *
	 * @param nameIdEncrypted
	 *            the nameIdEncrypted value to be set. Based on it the SP will encrypt the NameID or not
	 */
	public void setNameIdEncrypted(boolean nameIdEncrypted) {
		this.nameIdEncrypted = nameIdEncrypted;
	}

	/**
	 * Set the authnRequestsSigned setting value
	 *
	 * @param authnRequestsSigned
	 *            the authnRequestsSigned value to be set. Based on it the SP will sign Logout Request or not
	 */
	public void setAuthnRequestsSigned(boolean authnRequestsSigned) {
		this.authnRequestsSigned = authnRequestsSigned;
	}

	/**
	 * Set the logoutRequestSigned setting value
	 *
	 * @param logoutRequestSigned
	 *            the logoutRequestSigned value to be set. Based on it the SP will sign Logout Request or not
	 */
	public void setLogoutRequestSigned(boolean logoutRequestSigned) {
		this.logoutRequestSigned = logoutRequestSigned;
	}

	/**
	 * Set the logoutResponseSigned setting value
	 *
	 * @param logoutResponseSigned
	 *            the logoutResponseSigned value to be set. Based on it the SP will sign Logout Response or not
	 */
	public void setLogoutResponseSigned(boolean logoutResponseSigned) {
		this.logoutResponseSigned = logoutResponseSigned;
	}

	/**
	 * Set the wantMessagesSigned setting value
	 *
	 * @param wantMessagesSigned
	 *            the wantMessagesSigned value to be set. Based on it the SP expects the SAML Messages to be signed or not
	 */
	public void setWantMessagesSigned(boolean wantMessagesSigned) {
		this.wantMessagesSigned = wantMessagesSigned;
	}

	/**
	 * Set the wantAssertionsSigned setting value
	 *
	 * @param wantAssertionsSigned
	 *            the wantAssertionsSigned value to be set. Based on it the SP expects the SAML Assertions to be signed or not
	 */
	public void setWantAssertionsSigned(boolean wantAssertionsSigned) {
		this.wantAssertionsSigned = wantAssertionsSigned;
	}

	/**
	 * Set the wantAssertionsEncrypted setting value
	 *
	 * @param wantAssertionsEncrypted
	 *            the wantAssertionsEncrypted value to be set. Based on it the SP expects the SAML Assertions to be encrypted or not
	 */
	public void setWantAssertionsEncrypted(boolean wantAssertionsEncrypted) {
		this.wantAssertionsEncrypted = wantAssertionsEncrypted;
	}

	/**
	 * Set the wantNameId setting value
	 *
	 * @param wantNameId
	 *            the wantNameId value to be set. Based on it the SP expects a NameID
	 */
	public void setWantNameId(boolean wantNameId) {
		this.wantNameId = wantNameId;
	}

	/**
	 * Set the wantNameIdEncrypted setting value
	 *
	 * @param wantNameIdEncrypted
	 *            the wantNameIdEncrypted value to be set. Based on it the SP expects the NameID to be encrypted or not
	 */
	public void setWantNameIdEncrypted(boolean wantNameIdEncrypted) {
		this.wantNameIdEncrypted = wantNameIdEncrypted;
	}

	/**
	 * Set the signMetadata setting value
	 *
	 * @param signMetadata
	 *            the signMetadata value to be set. Based on it the SP will sign or not the metadata with the SP PrivateKey/Certificate
	 */
	public void setSignMetadata(boolean signMetadata) {
		this.signMetadata = signMetadata;
	}

	/**
	 * Set the requestedAuthnContext setting value
	 *
	 * @param requestedAuthnContext
	 *            the requestedAuthnContext value to be set on the AuthNRequest.
	 */
	public void setRequestedAuthnContext(List<String> requestedAuthnContext) {
		if (requestedAuthnContext != null) {
			this.requestedAuthnContext = requestedAuthnContext;
		}
	}

	/**
	 * Set the requestedAuthnContextComparison setting value
	 *
	 * @param requestedAuthnContextComparison
	 *            the requestedAuthnContextComparison value to be set.
	 */
	public void setRequestedAuthnContextComparison(String requestedAuthnContextComparison) {
		this.requestedAuthnContextComparison = requestedAuthnContextComparison;
	}

	/**
	 * Set the wantXMLValidation setting value
	 *
	 * @param wantXMLValidation
	 *            the wantXMLValidation value to be set. Based on it the SP will validate SAML messages against the XML scheme 
	 */
	public void setWantXMLValidation(boolean wantXMLValidation) {
		this.wantXMLValidation = wantXMLValidation;
	}

	/**
	 * Set the signatureAlgorithm setting value
	 *
	 * @param signatureAlgorithm
	 *            the signatureAlgorithm value to be set.
	 */
	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	/**
	 * Set the digestAlgorithm setting value
	 *
	 * @param digestAlgorithm
	 *            the digestAlgorithm value to be set.
	 */
	public void setDigestAlgorithm(String digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	/**
	 * Controls if unsolicited Responses are rejected if they contain an InResponseTo value.
	 *
	 * If false using a validate method {@link com.onelogin.saml2.authn.SamlResponse#isValid(String)} with a null argument will
	 * accept messages with any (or none) InResponseTo value.
	 *
	 * If true using these methods with a null argument will only accept messages with no InRespoonseTo value,
	 * and reject messages where the value is set.
	 *
	 * In all cases using validate with a specified request ID will only accept responses that have the same
	 * InResponseTo id set.
	 *
	 * @param rejectUnsolicitedResponsesWithInResponseTo whether to strictly check the InResponseTo attribute
	 */
	public void setRejectUnsolicitedResponsesWithInResponseTo(boolean rejectUnsolicitedResponsesWithInResponseTo) {
		this.rejectUnsolicitedResponsesWithInResponseTo = rejectUnsolicitedResponsesWithInResponseTo;
	}

	public boolean isRejectUnsolicitedResponsesWithInResponseTo() {
		return rejectUnsolicitedResponsesWithInResponseTo;
	}

	/**
	 * Set the compressRequest setting value
	 *
	 * @param compressRequest
	 *            the compressRequest value to be set.
	 */
	public void setCompressRequest(boolean compressRequest) {
		this.compressRequest = compressRequest;
	}

	/**
	 * @return the compressRequest setting value
	 */
	public boolean isCompressRequestEnabled() {
		return compressRequest;
	}

	/**
	 * Set the compressResponse setting value
	 *
	 * @param compressResponse
	 *            the compressResponse value to be set.
	 */
	public void setCompressResponse(boolean compressResponse) {
		this.compressResponse = compressResponse;
	}

	/**
	 * @return the compressResponse setting value
	 */
	public boolean isCompressResponseEnabled() {
		return compressResponse;
	}

	/**
	 * Sets whether Name IDs in parsed SAML messages should be trimmed.
	 * <p>
	 * Default is <code>false</code>, that is Name IDs are kept intact, as the SAML
	 * specification prescribes.
	 * 
	 * @param trimNameIds
	 *              set to <code>true</code> to trim parsed Name IDs, set to
	 *              <code>false</code> to keep them intact
	 */
	public void setTrimNameIds(boolean trimNameIds) {
		this.trimNameIds = trimNameIds;
	}
	
	/**
	 * Determines whether Name IDs should trimmed when extracting them from parsed
	 * SAML messages.
	 * <p>
	 * Default is <code>false</code>, that is Name IDs are kept intact, as the SAML
	 * specification prescribes.
	 * 
	 * @return <code>true</code> if Name IDs should be trimmed, <code>false</code>
	 *         otherwise
	 */
	public boolean isTrimNameIds() {
		return trimNameIds;
	}
	
	/**
	 * Sets whether attribute values in parsed SAML messages should be trimmed.
	 * <p>
	 * Default is <code>false</code>.
	 * 
	 * @param trimAttributeValues
	 *              set to <code>true</code> to trim parsed attribute values, set to
	 *              <code>false</code> to keep them intact
	 */
	public void setTrimAttributeValues(boolean trimAttributeValues) {
		this.trimAttributeValues = trimAttributeValues;
	}
	
	/**
	 * Determines whether attribute values should be trimmed when extracting them
	 * from parsed SAML messages.
	 * <p>
	 * Default is <code>false</code>.
	 * 
	 * @return <code>true</code> if attribute values should be trimmed,
	 *         <code>false</code> otherwise
	 */
	public boolean isTrimAttributeValues() {
		return trimAttributeValues;
	}
	
	/**
	 * Set contacts info that will be listed on the Service Provider metadata
	 * 
	 * @param contacts
	 *            the contacts to set
	 */
	protected final void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Set the organization info that will be published on the Service Provider metadata
	 *
	 * @param organization
	 *            the organization to set
	 */
	protected final void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * Checks the settings .
	 * 
	 * @return errors found on the settings data
	 */
	public List<String> checkSettings() {
		List<String> errors = new ArrayList<>(this.checkSPSettings());
		if (!spValidationOnly) { 
			errors.addAll(this.checkIdPSettings());
		}

		return errors;
	}
	
	/**
	 * Checks the IdP settings .
	 * 
	 * @return errors found on the IdP settings data
	 */
	public List<String> checkIdPSettings() {
		List<String> errors = new ArrayList<>();
		String errorMsg;

		if (!checkRequired(getIdpEntityId())) {
			errorMsg = "idp_entityId_not_found";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);
		}

		if (!checkRequired(this.getIdpSingleSignOnServiceUrl())) {
			errorMsg = "idp_sso_url_invalid";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);
		}

		if (!checkIdpx509certRequired() && !checkRequired(this.getIdpCertFingerprint())) {
			errorMsg = "idp_cert_or_fingerprint_not_found_and_required";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);			
		}

		if (!checkIdpx509certRequired() && this.getNameIdEncrypted()) {
			errorMsg = "idp_cert_not_found_and_required";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);
		}

		return errors;
	}

	/**
	 * Auxiliary method to check Idp certificate is configured.
	 * 
	 * @return true if the Idp Certificate settings are valid
	 */
	private boolean checkIdpx509certRequired () {
		if (this.getIdpx509cert() != null) {
			return true;
		}

		return this.getIdpx509certMulti() != null && !this.getIdpx509certMulti().isEmpty();
	}

	/**
	 * Checks the SP settings .
	 *
	 * @return errors found on the SP settings data
	 */
	public List<String> checkSPSettings() {
		List<String> errors = new ArrayList<>();
		String errorMsg;

		if (!checkRequired(getSpEntityId())) {
			errorMsg = "sp_entityId_not_found";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);
		}

		if (!checkRequired(getSpAssertionConsumerServiceUrl())) {
			errorMsg = "sp_acs_not_found";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);
		}

		if (this.getHsm() == null && (this.getAuthnRequestsSigned() || this.getLogoutRequestSigned()
			|| this.getLogoutResponseSigned() || this.getWantAssertionsEncrypted() || this.getWantNameIdEncrypted()) && !this.checkSPCerts()) {
			errorMsg = "sp_cert_not_found_and_required";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);
		}

		List<Contact> contacts = this.getContacts();
		if (!contacts.isEmpty()) {
			Set<String> validTypes = new HashSet<>();
			validTypes.add(Constants.CONTACT_TYPE_TECHNICAL);
			validTypes.add(Constants.CONTACT_TYPE_SUPPORT);
			validTypes.add(Constants.CONTACT_TYPE_ADMINISTRATIVE);
			validTypes.add(Constants.CONTACT_TYPE_BILLING);
			validTypes.add(Constants.CONTACT_TYPE_OTHER);
			for (Contact contact : contacts) {
				if (!validTypes.contains(contact.getContactType())) {
					errorMsg = "contact_type_invalid";
					errors.add(errorMsg);
					LOGGER.error(errorMsg);
				}
				if ((contact.getEmailAddresses().isEmpty()
				            || contact.getEmailAddresses().stream().allMatch(StringUtils::isEmpty))
				            && (contact.getTelephoneNumbers().isEmpty() || contact.getTelephoneNumbers()
				                        .stream().allMatch(StringUtils::isEmpty))
				            && StringUtils.isEmpty(contact.getCompany())
				            && StringUtils.isEmpty(contact.getGivenName())
				            && StringUtils.isEmpty(contact.getSurName())) {
					errorMsg = "contact_not_enough_data";
					errors.add(errorMsg);
					LOGGER.error(errorMsg);
				}
			}
		}

		Organization org = this.getOrganization();
		if (org != null && (org.getOrgDisplayName().isEmpty() || org.getOrgName().isEmpty() || org.getOrgUrl().isEmpty())) {
			errorMsg = "organization_not_enough_data";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);
		}

		if (this.getHsm() != null && this.getSPkey() != null) {
			errorMsg = "use_either_hsm_or_private_key";
			errors.add(errorMsg);
			LOGGER.error(errorMsg);
		}

		return errors;
	}

	/**
	 * Checks the x509 certficate/private key SP settings .
	 *
	 * @return true if the SP settings are valid
	 */
	public boolean checkSPCerts() {
		X509Certificate cert = getSPcert();
		PrivateKey key = getSPkey();

		return (cert != null && key != null);
	}
	
	/**
	 * Auxiliary method to check required properties.
	 *
	 * @param value
	 *            the current value of the property to be checked
	 *
	 *
	 * @return true if the SP settings are valid
	 */
	private boolean checkRequired(Object value) {
		if (value == null) {
			return false;
		}

		if (value instanceof String && ((String) value).isEmpty()) {
			return false;
		}

		if (value instanceof List && ((List<?>) value).isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Set the spValidationOnly value, used to check IdP data on checkSettings method
	 *
	 * @param spValidationOnly
	 *            the spValidationOnly value to be set
	 */
	public void setSPValidationOnly(boolean spValidationOnly)
	{
		this.spValidationOnly = spValidationOnly;
	}

	/**
	 * @return the spValidationOnly value
	 */
	public boolean getSPValidationOnly()
	{
		return this.spValidationOnly;
	}
	
	/**
	 * Gets the SP metadata. The XML representation.
	 *
	 * @return the SP metadata (xml)
	 *
	 * @throws CertificateEncodingException
	 */
	public String getSPMetadata() throws CertificateEncodingException {
		Metadata metadataObj = new Metadata(this);
		String metadataString = metadataObj.getMetadataString();

		// Check if must be signed
		boolean signMetadata = this.getSignMetadata();
		if (signMetadata) {
			// TODO Extend this in order to be able to read not only SP privateKey/certificate
			try {
				metadataString =  Metadata.signMetadata(
						metadataString,
						this.getSPkey(),
						this.getSPcert(),
						this.getSignatureAlgorithm(),
						this.getDigestAlgorithm()
				);
			} catch (Exception e) {				
				LOGGER.debug("Error executing signMetadata: " + e.getMessage(), e);
			}
		}

		return metadataString;
	}
	
	/**
	 * Validates an XML SP Metadata.
	 *
	 * @param metadataString Metadata's XML that will be validate
	 * 
	 * @return Array The list of found errors
	 *
	 * @throws Exception 
	 */
	public static List<String> validateMetadata(String metadataString) throws Exception {

		metadataString = metadataString.replace("<?xml version=\"1.0\"?>", "");

		Document metadataDocument = Util.loadXML(metadataString);

		List<String> errors = new ArrayList<>();

		if (!Util.validateXML(metadataDocument, SchemaFactory.SAML_SCHEMA_METADATA_2_0)) {
			errors.add("Invalid SAML Metadata. Not match the saml-schema-metadata-2.0.xsd");
		} else {
			Element rootElement = metadataDocument.getDocumentElement();
			if (!rootElement.getLocalName().equals("EntityDescriptor")) {
				errors.add("noEntityDescriptor_xml");
			} else {
				if (rootElement.getElementsByTagNameNS(Constants.NS_MD, "SPSSODescriptor").getLength() != 1) {
					errors.add("onlySPSSODescriptor_allowed_xml");
				} else {
					String validUntil = null;
					String cacheDuration = null;

					if (rootElement.hasAttribute("cacheDuration")) {
						cacheDuration = rootElement.getAttribute("cacheDuration");
					}

					if (rootElement.hasAttribute("validUntil")) {
						validUntil = rootElement.getAttribute("validUntil");
					}

					long expireTime = Util.getExpireTime(cacheDuration, validUntil);

					if (expireTime != 0 && Util.getCurrentTimeStamp() > expireTime) {
						errors.add("expired_xml");
					}
				}
			}
		}
		// TODO Validate Sign if required with Util.validateMetadataSign
		
		return errors;
	}
}
