/*
 * Copyright 2017 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cd.go.authorization.github;

import cd.go.authorization.github.models.AuthConfig;
import cd.go.authorization.github.models.AuthenticateWith;
import cd.go.authorization.github.models.GitHubConfiguration;
import org.kohsuke.github.GitHub;

import java.io.IOException;

import static cd.go.authorization.github.GitHubPlugin.LOG;

public class GitHubClientBuilder {

    public GitHub build(String usersAccessToken, AuthConfig authConfig) throws IOException {
        return createGitHub(usersAccessToken, authConfig.gitHubConfiguration());
    }

    private GitHub createGitHub(String accessToken, GitHubConfiguration gitHubConfiguration) throws IOException {
        if (gitHubConfiguration.authenticateWith() == AuthenticateWith.GITHUB_ENTERPRISE) {
            LOG.debug("Create GitHub connection to enterprise GitHub with token");
            return GitHub.connectToEnterprise(gitHubConfiguration.gitHubEnterpriseUrl(), accessToken);
        } else {
            LOG.debug("Create GitHub connection to public GitHub with token");
            return GitHub.connectUsingOAuth(accessToken);
        }
    }
}