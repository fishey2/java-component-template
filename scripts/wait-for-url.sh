#!/bin/bash

# Checks if one argument is provided
if [ -z "$1" ]
  then
    echo "Expected URL. No argument supplied."
    exit 128
fi

echo "Waiting for $1"

MAX_ATTEMPTS=10
CURRENT_ATTEMPT=0

# Check if the provided URL can be reached
until $(curl --output /dev/null --silent --head --fail "$1"); do

    printf '.'

    # Check and exit if max attempts have been reached
    if [ $CURRENT_ATTEMPT -eq $MAX_ATTEMPTS ]
      then
        echo "Maximum number of attempts ($MAX_ATTEMPTS) has been reached."
        exit 1
    fi

    # Increment Current Attempts
    ((CURRENT_ATTEMPT=CURRENT_ATTEMPT+1))

    # Sleep
    sleep 5
done

echo "Successfully Connected to $1"