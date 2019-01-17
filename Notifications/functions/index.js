'use strict'
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.sendNotification = functions.database.ref('/Notifications/{receiverUserId}/{notification_id}')
.onWrite((data, context) =>
{
 const receiverUserId = context.params.receiverUserId;
 const notification_id = context.params.notification_id;


 console.log('We have a notification to send to :' , receiverUserId);
 console.log('We have a notification to send to :' , notification_id );


 if (!data.after.val()) 
 {
  console.log('A notification has been deleted :' , notification_id);
  return null;
 }
 
 const  fromUser=admin.database().ref(`/Notifications/${receiverUserId}/${notification_id}`).once('value');
 return  fromUser.then(fromUserResult => 
 {
  const from_user_id  = fromUserResult.val().from;
  console.log('Got fromUser:' , fromUser, 'fromUserResult:', fromUserResult.val(), 'from_user_id', from_user_id);
  const SenderUserQuery = admin.database().ref(`/Users/${from_user_id}/username`).once('value');
   return SenderUserQuery.then(senderUserNameResult=>{
   const SenderUserName=senderUserNameResult.val();
   console.log('SenderUserName' , SenderUserName, 'senderUserNameResult', senderUserNameResult );


 const DeviceToken = admin.database().ref(`/Users/${receiverUserId}/device_token`).once('value');

 return DeviceToken.then(result => 
 {
  const token_id = result.val();

  const payload = 
  {
   notification:
   {
    
    title: "New Chat Request",
    body: `${SenderUserName} has sent you a friend request`,
    icon: "default"
     },
   data: {
    
    from_user_id:from_user_id
   }
  
  }; 
  
  return admin.messaging().sendToDevice(token_id, payload)
  .then(response => 
   { 
    console.log('This was a notification feature.');
   });
   
   });
  
   });
 });
});﻿