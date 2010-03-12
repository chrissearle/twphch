/*
 * Copyright 2010 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import net.chrissearle.flickrvote.flickr.FlickrLoginService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.UserDAO;
import net.chrissearle.flickrvote.flickr.impl.FlickrJLoginService;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;

public class LoginServiceTest {
    private static final String ERROR_CODE = "ERROR_CODE";
    private static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    private static final String TEST_URL_READ = "http://read";
    private static final String TEST_URL_WRITE = "http://write";
    private static final String FROB = "FROB";

    @Test(expected = FlickrServiceException.class)
    public void testGetUrlError() throws FlickrException, IOException, SAXException {
        Flickr flickr = mock(Flickr.class);
        UserDAO dao = mock(UserDAO.class);

        AuthInterface auth = mock(AuthInterface.class);
        when(flickr.getAuthInterface()).thenReturn(auth);
        when(auth.getFrob()).thenThrow(new FlickrException(ERROR_CODE, ERROR_MESSAGE));

        FlickrLoginService service = new FlickrJLoginService(flickr, dao);

        try {
            service.getLoginUrl();
        } catch (FlickrServiceException e) {
            assertEquals("Exception message incorrect", "com.aetrion.flickr.FlickrException: " + ERROR_CODE + ": " + ERROR_MESSAGE, e.getMessage());
            throw e;
        }
    }

    @Test
    public void testGetUrl() throws FlickrException, IOException, SAXException {
        FlickrLoginService service = setupServiceForLoginUrlTests();

        URL url = service.getLoginUrl();

        assertEquals("Url was not correct", TEST_URL_READ, url.toExternalForm());
    }

    @Test
    public void testGetUrlRead() throws FlickrException, IOException, SAXException {
        FlickrLoginService service = setupServiceForLoginUrlTests();

        URL url = service.getLoginUrl(false);

        assertEquals("Url was not correct", TEST_URL_READ, url.toExternalForm());
    }

    @Test
    public void testGetUrlTrue() throws FlickrException, IOException, SAXException {
        FlickrLoginService service = setupServiceForLoginUrlTests();

        URL url = service.getLoginUrl(true);

        assertEquals("Url was not correct", TEST_URL_WRITE, url.toExternalForm());
    }

    private FlickrLoginService setupServiceForLoginUrlTests() throws IOException, SAXException, FlickrException {
        Flickr flickr = mock(Flickr.class);
        UserDAO dao = mock(UserDAO.class);

        AuthInterface auth = mock(AuthInterface.class);

        when(flickr.getAuthInterface()).thenReturn(auth);
        when(auth.getFrob()).thenReturn(FROB);
        when(auth.buildAuthenticationUrl(Permission.READ, FROB)).thenReturn(new URL(TEST_URL_READ));
        when(auth.buildAuthenticationUrl(Permission.WRITE, FROB)).thenReturn(new URL(TEST_URL_WRITE));

        FlickrLoginService service = new FlickrJLoginService(flickr, dao);
        return service;
    }
}