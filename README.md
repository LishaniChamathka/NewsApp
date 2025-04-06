#  QuickNews - Mobile News Reporting App (Kotlin)

QuickNews is a mobile news reporting application built using Kotlin, designed to streamline the process of collecting and publishing news. The app features a role-based access system where reporters can submit stories and admins can manage approvals for publishing.

---

##  Project Overview

The primary goal of QuickNews is to provide a simple, fast, and efficient way for field reporters to submit news with images, which will then be reviewed and approved by an admin before being published.

---

##  User Roles & Workflow

###  Role-Based Access

- **Reporter (User)**:
  - Can log in and access a personalized dashboard.
  - Create and submit news articles.
  - Fill in fields like **Title**, **News Type**, **Image**, and **Content**.

- **Admin**:
  - Access the admin dashboard after login.
  - View all submitted news articles.
  - **Approve or reject** submitted news.
  - Only approved news gets published and marked as "Live"
 
##  Features

-  **Login System** with Role Verification (Admin / User)
-  **Homepage Navigation** based on user type
-  **News Submission Form** (Image, Title, Type, Content)
-  **Submission to Admin** for review
-  **Approval Workflow** by Admin
-  **Published News Section** after approval

##  Tech Stack

 -  **Language**       - Kotlin                       
 -  **Framework**      - Android SDK                              
 -  **UI Components**  -  XML        
 -  **Backend**        - Firebase      
 -  **Database**       - Firestore 
 -  **Auth**           - Firebase Auth   
 -  **Image Upload**   - Firebase Storage 

## Future Improvements

- Push Notifications for approval status

- Offline support and caching

- Editor's comments for submitted news

- Advanced analytics for published stories
