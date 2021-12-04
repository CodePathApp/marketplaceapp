Original App Design Project - README Template
===

# APP_NAME_HERE

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)


## Overview
### Description
A marketplace application where users can buy and sell items. There will be filters associated with each item and people will be able to message each other to negotiate with on another.

### App Evaluation
- **Category:**  Utility/Shopping
- **Mobile:**  This app is suitable to be more than a mobile aplication, but since everyone is using their phones all the time is more comfortable to buy/sell from them.
- **Story:**  Users can create post with the description and picture of the item they want to buy, also to create their own post to sell and communicate with other users via chat in the app
- **Market:**  Almost every person who wants to buy something
- **Habit:** This app can be used as often as the person wants. They can open the app when they're looking for something specfically or looking for good deals. The app will also make uploading new items to sell extremely easy.
- **Scope:** Any type of item will be able to be sold 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
- [x] User logs in to access previous chats and preference settings
- [x] Users can post new items they want to sell,and set the price. 
- [x] The main feed will have posts, description, price on items.
- [ ] Profile pages for each user
- [ ] Messages feed where user can message each other and negotiate.


### 2. Screen Archetypes

* Login 
* Register - User signs up or logs into their account
   * Upon Download/Reopening of the application, the user is prompted to log in to gain access to their profile information to be properly matched with another person. 
* Messaging Screen - Chat for users to communicate (direct 1-on-1)
   * People will be able to negotiate with one another
* Profile Screen 
   * Allows user to upload a photo. It will also contain their rating and items they are selling.
* Post Screen 
    * Users will upload images of the item they want to sell, set the price and add filters. 

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Feed
* Post
* Messages
* Profile

**Flow Navigation** (Screen to Screen)

* Forced Log-in -> Account creation if no log in is available
* Feed -> Load different items being sold.
* Post -> Create a post
* Messages -> Jumps to Chat
* Profile -> Text field to be modified. 



## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>


## Schema 

### Models
Posts
| Property| Type|Description|
|---|---|---|
| PostID  | INT  |  unique id |
| User   |Pointer tp User   | the creator of post  |
| price   | float   | the price of the item  |
| date   | Date   | Date created  |
| sold   | float   | the price the item sold for |

User

| Property| Type|Description|
|---|---|---|
| ID  | INT  |  unique id |
| first_name   | varchar  | user first name |
| last_name    | varchar   | user last name  |
| username    | varchar   | username  |
| password    | varchar   | password  |
| profile_image    | File   | profile picture  |

Images

| Property| Type|Description|
|---|---|---|
| ID  | INT  |  unique id |
|Image   |File   | image of item  |
| PostId   | pointer to Posts   | the id of the post the image is associated with  |

Messages

| Property| Type|Description|
|---|---|---|
| ID  | INT  |  unique id |
| From_user  | pointer to user  |  user that sent the message |
|to_user   |pointer to user   | user that recieves the message  |
| message   | varchar   | the message being sent  |
| Date   | Date   | date sent  |
### Networking


* Home Feed Screen
    * (Read/GET) Query all posts by date 
* Create Post Screen
    * (Create/POST) Create a new post object
* Login/Signup  Screen
    * (Create/POST) Create a new User 
        ``` 
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername("joestevens");
        user.setPassword("secret123");
        user.setEmail("email@example.com");
        // Set custom properties
        user.put("phone", "650-253-0000");
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
          public void done(ParseException e) {
            if (e == null) {
              // Hooray! Let them use the app now.
            } else {
              // Sign up didn't succeed. Look at the ParseException
              // to figure out what went wrong
            }
          }
        });
        ```
     * (Read/GET) Query username and password to sign in    
* Profile  Screen
    * (Read/GET) Query logged in user object
    * (Update/PUT) Update user profile image
* message screen
    * (Create/POST) Create a new message 
    * (Read/GET) Query all messages between the users
