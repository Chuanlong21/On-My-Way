
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (!/^\d{10}$/.test(val)) {
    return false
  } else {
    return true
  }
}

//校验账号
function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter username"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("Username length should be between 3-20"))
  } else {
    callback()
  }
}

//校验姓名
function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter your full name"))
  } else if (value.length > 12) {
    callback(new Error("The length should be between 1-12"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){
  // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
  if (value == "") {
    callback(new Error("Please enter phone number "))
  } else if (!isCellPhone(value)) {
    callback(new Error("Please enter a valid phone number!"))
  } else {
    callback()
  }
}


function validID (rule,value,callback) {
  // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
  let reg = /^\d{6}$/
  if(value == '') {
    callback(new Error('Please enter your employee ID'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('Invalid employee ID(it has to be 6 numbers)'))
  }
}