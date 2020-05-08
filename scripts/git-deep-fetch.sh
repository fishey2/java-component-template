# Checks if repository is shallow or not
if ! $(git rev-parse --is-shallow-repository)
  then
    echo "This is not a shallow repository, quitting ($(git rev-parse --is-shallow-repository))"
  else
    echo "Fetching with --unshallow arg "
    git fetch --unshallow
fi

