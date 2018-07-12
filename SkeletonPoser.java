/*------------------------------------------------------------
Step In: Julie Chea
CITA 945
-------------------------------------------------------------*/

/**---------------------------------------------------------------
Classes to Import
----------------------------------------------------------------*/

import SimpleOpenNI.*;
import ddf.minim.*;


// Kinect object to interact with Kinect
SimpleOpenNI kinect;

// Audio Variables
Minim minim;
AudioPlayer player;

// Poser object
SkeletonPoser frame1Pose; // hands up
SkeletonPoser frame2Pose; // dragon dance
SkeletonPoser frame3Pose; // unity

// Image variables
PImage img1;
PImage img2;
PImage img3;
PImage rgbImage;

// mapping users
int[] userMapping;
int[] users = new int[5];

PVector projCoM;

int stage = 1;


void setup() { 
     size(640, 480);
     
     
     kinect = new SimpleOpenNI(this);
     // enable RGB sensor
     kinect.enableRGB();
     // enable depth sensor
     kinect.enableDepth();
     // enable skeleton generation for joints
     kinect.enableUser();
     kinect.setMirror(true);
     
     size(kinect.depthWidth(), kinect.depthHeight()); 
     background(255);
     img1 = loadImage("handsup.png");
     img2 = loadImage("liondance1.png");
     img3 = loadImage("mlk.png");
     
     
     
    // initialize the minim object
     minim = new Minim(this);
     // and load the success sound file
     player = minim.loadFile("win.wav");
    
    
    // ==================== Hands Up pose setup ===============================
     // initialize the pose object for HandsUp
     frame1Pose = new SkeletonPoser(kinect); 
     
    
     // rules for the right arm
     frame1Pose.addRule(SimpleOpenNI.SKEL_RIGHT_HAND, 
     PoseRule.ABOVE,
     SimpleOpenNI.SKEL_RIGHT_ELBOW);
     
     frame1Pose.addRule(SimpleOpenNI.SKEL_RIGHT_ELBOW,
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_RIGHT_SHOULDER);
     
     frame1Pose.addRule(SimpleOpenNI.SKEL_RIGHT_HAND,
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_RIGHT_SHOULDER);
     
     
     
     // rules for the left arm
     frame1Pose.addRule(SimpleOpenNI.SKEL_LEFT_HAND,
     PoseRule.ABOVE,
     SimpleOpenNI.SKEL_LEFT_ELBOW);
     
     frame1Pose.addRule(SimpleOpenNI.SKEL_LEFT_ELBOW,
     PoseRule.LEFT_OF,
     SimpleOpenNI.SKEL_LEFT_SHOULDER);
     
     frame1Pose.addRule(SimpleOpenNI.SKEL_LEFT_HAND,
     PoseRule.LEFT_OF,
     SimpleOpenNI.SKEL_LEFT_ELBOW);
     
     
//     // rules for the left leg
//     frame1Pose.addRule(SimpleOpenNI.SKEL_LEFT_KNEE,
//     PoseRule.BELOW,
//     SimpleOpenNI.SKEL_LEFT_HIP);
//     frame1Pose.addRule(SimpleOpenNI.SKEL_LEFT_KNEE,
//     PoseRule.LEFT_OF,
//     SimpleOpenNI.SKEL_LEFT_HIP);
//     frame1Pose.addRule(SimpleOpenNI.SKEL_LEFT_FOOT,
//     PoseRule.BELOW,
//     SimpleOpenNI.SKEL_LEFT_KNEE);
//     frame1Pose.addRule(SimpleOpenNI.SKEL_LEFT_FOOT,
//     PoseRule.LEFT_OF,
//     SimpleOpenNI.SKEL_LEFT_KNEE);
//
//    // rules for the right leg
//     frame1Pose.addRule(SimpleOpenNI.SKEL_RIGHT_KNEE,
//     PoseRule.BELOW,
//     SimpleOpenNI.SKEL_RIGHT_HIP);
//    
//     frame1Pose.addRule(SimpleOpenNI.SKEL_RIGHT_KNEE,
//     PoseRule.RIGHT_OF,
//     SimpleOpenNI.SKEL_RIGHT_HIP);
//     
//     frame1Pose.addRule(SimpleOpenNI.SKEL_RIGHT_FOOT,
//     PoseRule.BELOW,
//     SimpleOpenNI.SKEL_RIGHT_KNEE);
//     frame1Pose.addRule(SimpleOpenNI.SKEL_RIGHT_FOOT,
//     PoseRule.RIGHT_OF,
//     SimpleOpenNI.SKEL_RIGHT_KNEE);
     
     // ==================== end Hands Up pose setup ======================
     
     // ==================== start Lion Dance pose setup ==================
     
     frame2Pose = new SkeletonPoser(kinect); 
     
    
     // rules for the right arm
     frame2Pose.addRule(SimpleOpenNI.SKEL_RIGHT_HAND, 
     PoseRule.ABOVE,
     SimpleOpenNI.SKEL_RIGHT_ELBOW);
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_RIGHT_ELBOW, 
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_RIGHT_SHOULDER);
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_RIGHT_HAND, 
     PoseRule.ABOVE,
     SimpleOpenNI.SKEL_HEAD);
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_RIGHT_HAND, 
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_HEAD);
     
     
     // rules for right leg
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_RIGHT_KNEE, 
     PoseRule.BELOW,
     SimpleOpenNI.SKEL_RIGHT_HIP);
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_RIGHT_FOOT, 
     PoseRule.BELOW,
     SimpleOpenNI.SKEL_RIGHT_KNEE);
     
     // rules for left arm
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_LEFT_HAND, 
     PoseRule.ABOVE,
     SimpleOpenNI.SKEL_LEFT_ELBOW);
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_LEFT_ELBOW, 
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_LEFT_SHOULDER);
     
     // rules for left leg
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_LEFT_KNEE, 
     PoseRule.BELOW,
     SimpleOpenNI.SKEL_LEFT_HIP);
     
     frame2Pose.addRule(SimpleOpenNI.SKEL_LEFT_KNEE, 
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_LEFT_HIP);

     frame2Pose.addRule(SimpleOpenNI.SKEL_LEFT_FOOT, 
     PoseRule.BELOW,
     SimpleOpenNI.SKEL_LEFT_KNEE);

      frame2Pose.addRule(SimpleOpenNI.SKEL_LEFT_FOOT, 
     PoseRule.LEFT_OF,
     SimpleOpenNI.SKEL_LEFT_KNEE);     
     
     // ===================== end Lion Dance pose setup ======================
     
     // ===================== unity pose setup ===============================
     
      frame3Pose = new SkeletonPoser(kinect); 
     
    
     // rules for the right arm
     frame1Pose.addRule(SimpleOpenNI.SKEL_RIGHT_SHOULDER, 
     PoseRule.ABOVE,
     SimpleOpenNI.SKEL_RIGHT_ELBOW);
  
  
     frame3Pose.addRule(SimpleOpenNI.SKEL_RIGHT_ELBOW, 
     PoseRule.BELOW,
     SimpleOpenNI.SKEL_RIGHT_SHOULDER);

     frame3Pose.addRule(SimpleOpenNI.SKEL_RIGHT_ELBOW, 
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_RIGHT_SHOULDER);
     
     frame3Pose.addRule(SimpleOpenNI.SKEL_RIGHT_ELBOW, 
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_RIGHT_HAND);
     
     frame3Pose.addRule(SimpleOpenNI.SKEL_RIGHT_HAND, 
     PoseRule.BELOW,
     SimpleOpenNI.SKEL_RIGHT_ELBOW);
     
     frame3Pose.addRule(SimpleOpenNI.SKEL_RIGHT_HAND, 
     PoseRule.LEFT_OF,
     SimpleOpenNI.SKEL_RIGHT_ELBOW);
     
       // rules for the left arm
     frame3Pose.addRule(SimpleOpenNI.SKEL_LEFT_SHOULDER, 
     PoseRule.ABOVE,
     SimpleOpenNI.SKEL_LEFT_ELBOW);
  
  
     frame3Pose.addRule(SimpleOpenNI.SKEL_LEFT_ELBOW, 
     PoseRule.BELOW,
     SimpleOpenNI.SKEL_LEFT_SHOULDER);

     frame3Pose.addRule(SimpleOpenNI.SKEL_LEFT_ELBOW, 
     PoseRule.LEFT_OF,
     SimpleOpenNI.SKEL_LEFT_SHOULDER);
     
     frame3Pose.addRule(SimpleOpenNI.SKEL_LEFT_ELBOW, 
     PoseRule.LEFT_OF,
     SimpleOpenNI.SKEL_LEFT_HAND);
     
     frame3Pose.addRule(SimpleOpenNI.SKEL_LEFT_HAND, 
     PoseRule.BELOW,
     SimpleOpenNI.SKEL_LEFT_ELBOW);
     
     frame3Pose.addRule(SimpleOpenNI.SKEL_LEFT_HAND, 
     PoseRule.RIGHT_OF,
     SimpleOpenNI.SKEL_LEFT_ELBOW);
     
     
     // ====================== end unity pose setup =============================
     strokeWeight(5);
}

//***********************************************************88
//void draw() { 
//  
//    image(backgroundImg,0,0, 640, 480);
//    // background(0);
//     kinect.update();
//     
//     //image(kinect.depthImage(), 0, 0);
//     
//     // grab rgb image
//     rgbImage = kinect.rgbImage();
//     loadPixels();
//     userMapping = kinect.userMap();
//    
//    // for the length of the pixels tracked, color them
//    // in with the rgb camera
//    for (int i =0; i < userMapping.length; i++) {
//      // if the pixel is part of the user
//      if (userMapping[i] != 0) {
//        // set the sketch pixel to the rgb camera pixel
//        pixels[i] = rgbImage.pixels[i]; 
//      } // if (userMap[i] != 0)
//    } // (int i =0; i < userMap.length; i++)
//     
//    // update any changed pixels
//    updatePixels();
//     
//     IntVector userList = new IntVector();
//     kinect.getUsers(userList);
//     
//     if (userList.size() > 0) {
//       int userId = userList.get(0);
//       if( kinect.isTrackingSkeleton(userId)) {
//         // check to see if the user
//         // is in the pose
//         if(pose.check(userId)){ 
//           //if they are, set the color white
//           stroke(255); 
//           println("pose found");
////           // and start the song playing
//           if(!player.isPlaying()) {
//             player.play();
//     }
//     } else {
//     // otherwise set the color to red
//     // and don’t start the song
//     stroke(255,0,0); 
//     }
//     // draw the skeleton in whatever color we chose
//     //drawSkeleton(userId); 
//     }
//     }
   
//    
//     //drawing ellipse on the center of mass of user to find closest user
//      users=kinect.getUsers(); //get array of IDs of all users present 
// if (users.length > 0){   // ensure at least one user is being tracked
//    projCoM = pickUser(); // find the closest user
//    fill(255,0,0);
//    ellipse(projCoM.x, projCoM.y, 25, 25);
// }
//     
//}


//***********************************************************************

void draw(){
  
   
  
  if (stage == 1){
    image(img1, 0,0,640,480);
    fill(0);
     textSize(20);
     text("Step into the missing pose!", 350, 450);
    kinect.update();
     
     //image(kinect.depthImage(), 0, 0);
     
     // grab rgb image
     rgbImage = kinect.rgbImage();
     loadPixels();
     userMapping = kinect.userMap();
    
    // for the length of the pixels tracked, color them
    // in with the rgb camera
    for (int i =0; i < userMapping.length; i++) {
      // if the pixel is part of the user
      if (userMapping[i] == 1) {
        // set the sketch pixel to the rgb camera pixel
        pixels[i] = rgbImage.pixels[i]; 
      } // if (userMap[i] != 0)
    } // (int i =0; i < userMap.length; i++)
     
    // update any changed pixels
    updatePixels();
     
     IntVector userList = new IntVector();
     kinect.getUsers(userList);
     
     if (userList.size() ==1 ) {
       int userId = userList.get(0);
       if( kinect.isTrackingSkeleton(userId)) {
         // check to see if the user
         // is in the pose
         //if((frame1Pose.check(userId) && isBetween(projCoM, 148,159,272,159,152,261,283,261))){ //**************
          if(frame1Pose.check(userId)){
           //if they are, set the color white
           stroke(255); 
           println("pose found");
//           // and start the song playing
           if(!player.isPlaying()) {
             player.play();
             
     }
            stage = 2;
     } else {
     // otherwise set the color to red
     // and don’t start the song
     stroke(255,0,0); 
     }
     // draw the skeleton in whatever color we chose
     //drawSkeleton(userId); 
     }
     }
   
    
     //drawing ellipse on the center of mass of user to find closest user
      users=kinect.getUsers(); //get array of IDs of all users present 
 if (users.length > 0){   // ensure at least one user is being tracked
    projCoM = pickUser(); // find the closest user
    fill(255,0,0);
    ellipse(projCoM.x, projCoM.y, 25, 25);
 }
   
    
  } // == end stage 1 ==
  
  
  // ===== second frame ====
  
  if (stage == 2){ // ************** adjust to frame 2
    image(img2, 0,0, 640, 480);
    
     fill(0);
     textSize(20);
     text("Step into the missing pose!", 350, 450);
    kinect.update();
     
     //image(kinect.depthImage(), 0, 0);
     
     // grab rgb image
     rgbImage = kinect.rgbImage();
     loadPixels();
     userMapping = kinect.userMap();
    
    // for the length of the pixels tracked, color them
    // in with the rgb camera
    for (int i =0; i < userMapping.length; i++) {
      // if the pixel is part of the user
      if (userMapping[i] == 1) {
        // set the sketch pixel to the rgb camera pixel
        pixels[i] = rgbImage.pixels[i]; 
      } // if (userMap[i] != 0)
    } // (int i =0; i < userMap.length; i++)
     
    // update any changed pixels
    updatePixels();
     
     IntVector userList = new IntVector();
     kinect.getUsers(userList);
     
     if (userList.size() ==1 ) {
       int userId = userList.get(0);
       if( kinect.isTrackingSkeleton(userId)) {
         // check to see if the user
         // is in the pose
         //if((frame1Pose.check(userId) && isBetween(projCoM, 148,159,272,159,152,261,283,261))){ //**************
         if(frame2Pose.check(userId)){
           //if they are, set the color white
           stroke(255); 
           println("pose found");
//           // and start the song playing
           if(!player.isPlaying()) {
             player.play();
             
     }
            stage = 3;
     } else {
     // otherwise set the color to red
     // and don’t start the song
     stroke(255,0,0); 
     }
     // draw the skeleton in whatever color we chose
    // drawSkeleton(userId); 
     }
     }
   
    
     //drawing ellipse on the center of mass of user to find closest user
      users=kinect.getUsers(); //get array of IDs of all users present 
 if (users.length > 0){   // ensure at least one user is being tracked
    projCoM = pickUser(); // find the closest user
    fill(255,0,0);
    ellipse(projCoM.x, projCoM.y, 25, 25);
 }
 
 
 // == end stage 2 ==
  }
  
  
  // ===== stage 3 ======
  
  
  if (stage == 3){ 
    image(img3, 0,0, 640, 480);
    
     fill(255);
     textSize(20);
     text("Step into the missing pose!", 350, 450);
     
    kinect.update();
     
     //image(kinect.depthImage(), 0, 0);
     
     // grab rgb image
     rgbImage = kinect.rgbImage();
     loadPixels();
     userMapping = kinect.userMap();
    
    // for the length of the pixels tracked, color them
    // in with the rgb camera
    for (int i =0; i < userMapping.length; i++) {
      // if the pixel is part of the user
      if (userMapping[i] == 1) {
        // set the sketch pixel to the rgb camera pixel
        pixels[i] = rgbImage.pixels[i]; 
      } // if (userMap[i] != 0)
    } // (int i =0; i < userMap.length; i++)
     
    // update any changed pixels
    updatePixels();
     
     IntVector userList = new IntVector();
     kinect.getUsers(userList);
     
     if (userList.size() ==1 ) {
       int userId = userList.get(0);
       if( kinect.isTrackingSkeleton(userId)) {
         // check to see if the user
         // is in the pose
         //if((frame1Pose.check(userId) && isBetween(projCoM, 148,159,272,159,152,261,283,261))){ //**************
         if(frame3Pose.check(userId)){
           //if they are, set the color white
           stroke(255); 
           println("pose found");
//           // and start the song playing
           if(!player.isPlaying()) {
             player.play();
             
     }
            stage = 1; // start from the beginning
     } else {
     // otherwise set the color to red
     // and don’t start the song
     stroke(255,0,0); 
     }
     // draw the skeleton in whatever color we chose
    // drawSkeleton(userId); 
     }
     }
   
    
     //drawing ellipse on the center of mass of user to find closest user
      users=kinect.getUsers(); //get array of IDs of all users present 
 if (users.length > 0){   // ensure at least one user is being tracked
    projCoM = pickUser(); // find the closest user
    fill(255,0,0);
    ellipse(projCoM.x, projCoM.y, 25, 25);
 }
     }
}




void drawSkeleton(int userId) {
     kinect.drawLimb(userId, SimpleOpenNI.SKEL_HEAD,
     SimpleOpenNI.SKEL_NECK);
     kinect.drawLimb(userId, SimpleOpenNI.SKEL_NECK,
     SimpleOpenNI.SKEL_LEFT_SHOULDER);
    
     kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER,
     SimpleOpenNI.SKEL_LEFT_ELBOW);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_LEFT_ELBOW,
     SimpleOpenNI.SKEL_LEFT_HAND);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_NECK,
     SimpleOpenNI.SKEL_RIGHT_SHOULDER);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_RIGHT_SHOULDER,
     SimpleOpenNI.SKEL_RIGHT_ELBOW);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_RIGHT_ELBOW,
     SimpleOpenNI.SKEL_RIGHT_HAND);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_LEFT_SHOULDER,
     SimpleOpenNI.SKEL_TORSO);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_RIGHT_SHOULDER,
     SimpleOpenNI.SKEL_TORSO);
     kinect.drawLimb(userId, SimpleOpenNI.SKEL_TORSO,
     SimpleOpenNI.SKEL_LEFT_HIP);
     kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_HIP,
     SimpleOpenNI.SKEL_LEFT_KNEE);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_LEFT_KNEE,
     SimpleOpenNI.SKEL_LEFT_FOOT);
     kinect.drawLimb(userId, SimpleOpenNI.SKEL_TORSO,
     SimpleOpenNI.SKEL_RIGHT_HIP);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_RIGHT_HIP,
     SimpleOpenNI.SKEL_RIGHT_KNEE);
     kinect.drawLimb(userId,
     SimpleOpenNI.SKEL_RIGHT_KNEE,
     SimpleOpenNI.SKEL_RIGHT_FOOT);
     kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP,
     SimpleOpenNI.SKEL_LEFT_HIP);
}


void drawLimb(int userId, int jointType1, int jointType2)
{
   PVector jointPos1 = new PVector();
   PVector jointPos2 = new PVector();
   float confidence;
   // draw the joint position
   confidence = kinect.getJointPositionSkeleton(userId, jointType1, jointPos1);
   confidence = kinect.getJointPositionSkeleton(userId, jointType2, jointPos2);
   line(jointPos1.x, jointPos1.y, jointPos1.z,
   jointPos2.x, jointPos2.y, jointPos2.z);
}


// user-tracking callbacks!
void onNewUser(SimpleOpenNI kinect, int userId) {
   println("start pose detection");
   kinect.startTrackingSkeleton( userId);
}

void onLostUser(SimpleOpenNI kinect, int userId){
  println("User Lost- UserID: " + userId);
}



PVector pickUser(){ // looks through users tracked and return the center of mass for the closest user (projCoM)
  
  int smallest = 5000;
  PVector[] potentialUsers = new PVector[users.length];

 for (int i = 0; i < users.length; i++){ // Find the closest user
 int uid = users[i];

 // Get the center of mass for the user and convert it into a projected format
 PVector realCoM=new PVector(); // real world center of mass
 kinect.getCoM(uid,realCoM);
  projCoM=new PVector(); // projective centert of mass (for 2D display)
 kinect.convertRealWorldToProjective(realCoM, projCoM); // convert
 potentialUsers[i] = projCoM; // add the users center of mass inforamtion to an array of potential users
 }
 for (int i = 0; i < potentialUsers.length; i++){ // Find the closest user
   //println(potentialUsers[i].z); // check distance
   if (potentialUsers[i].z < smallest){
     projCoM = potentialUsers[i];
   }
 }
 return projCoM; //return the closest center of mass
 
}


// to use to determine where CoM should fall within images
void mouseClicked(){
  print("x: ", mouseX, "y: ", mouseY);

}

// detecting whether user's center of mass is within keyhole shape
boolean isBetween(PVector projCoM, int centerX1, int centerY1, 
                                   int centerX2, int centerY2,
                                   int centerX3, int centerY3,
                                   int centerX4, int centerY4){
         
       if ((projCoM.x > centerX1) && (projCoM.y > centerY1) &&
          (projCoM.x < centerX2) && (projCoM.y > centerY2) &&
          (projCoM.x > centerX3) && (projCoM.y > centerY3) &&
          (projCoM.x < centerX4) && (projCoM.y < centerY4)){
          
            return true;
            
          }
          
        else{
          return false;
        }
                                   }
