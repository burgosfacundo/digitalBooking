function isValidEmail(email) {
  const regex_pattern =
    /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  if (regex_pattern.test(email)) {
    return true;
  }

  return false;
}

function isValidPassword(password) {
  return password.length >= 6 ? true : false;
}

function passwordsMatch(password, passwordConfirmation) {
  return password === passwordConfirmation ? true : false;
}

export { isValidEmail, isValidPassword, passwordsMatch };
