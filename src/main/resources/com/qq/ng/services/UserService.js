QQApp.service('userService', function() {
  var user;

  var setUser = function(newUser) {
      user = newUser;
  };
  
  var getUser = function(){
      return user;
  };
  
  return {
      setUser: setUser,
      getUser: getUser
      };
  });