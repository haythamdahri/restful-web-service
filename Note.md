== Restful web service
Social Media Web Service

== Users Resources
- List of users: GET | /users
- Specific user: GET | /users/{userId}
- Add a new user: POST | /users
- Update an existing user: PUT | /users
- Delete an existing user: DELETE | /users

== Posts Resources
- List of posts: GET | /posts
- Specific post: GET | /posts/{postId}
- Add a new post: POST | /posts
- Update an existing post: PUT | /posts
- Delete an existing post: DELETE | /posts

== Users and Posts  
- User's posts: GET | /users/{userId}/posts
- User's specific post: GET | /users/{userId}/posts/{postId}

== Exception handling