# DOCKER RELATED STEPS
_testComposeUp:
	echo "Bringing up test docker dependencies"
	docker-compose up -d

_testComposeDown:
	echo "Tearing down test docker dependencies"
	docker-compose down

_functionalComposeBuild: package
	echo "Packaging service to .jar"
	docker-compose -f docker-compose-test.yml build

_functionalComposeUp: _functionalComposeBuild
	echo "Building docker image"
	docker-compose -f docker-compose-test.yml up -d

_functionalComposeDown:
	echo "Bringing down the docker containers [functional]"
	docker-compose -f docker-compose-test.yml down

# HELPER SCRIPTS
_waitForLocalService:
	./scripts/wait-for-url.sh http://localhost:8080

_gitFetchUnshallow:
	./scripts/git-deep-fetch.sh

# GRADLE RELATED STEPS
_setUpGradle:
	echo "Adding execure permissions to Gradle"
	chmod +x ./gradlew

test: _setUpGradle
	echo "Running Unit tests"
	./gradlew test

testIntegration: _setUpGradle _testComposeUp
	echo "Running Integration tests"
	./gradlew testIntegration
	#make _testComposeDown

testFunctional: _setUpGradle _functionalComposeUp
	make _waitForLocalService
	echo "Running Functional tests"
	./gradlew testFunctional
	#make _functionalComposeDown

analyseWithJacoco: _setUpGradle
	echo "Running jacoco analysis"
	./gradlew jacocoTestCoverageVerification

analyseWithSonar: _gitFetchUnshallow _setUpGradle
	echo "Running sonar analysis"
	./gradlew sonarqube

package: _setUpGradle
	echo "Packaging service"
	./gradlew bootJar