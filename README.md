# News-Portal

## Author

* Kibet Ronald

## Description

* An organisational news portal api to: create user ,news-department and general and also view the recorded information.

### Prerequisites

* To run this software, you need an editor with java support, java- version 11 or higher,heroku cli.
* Install java 11 using the commands:
  `$ sudo apt update`
  `$ sudo apt install openjdk-11-jre-headless`
* Install heroku using the commands:
  `$ sudo snap install --classic heroku`
* Install postgresql:
  `$ sudo apt-get install postgresql postgresql-contrib libpq-dev`


### Setup

* To access this project locally in your machine,use the command:

`$ git clone https://github.com/ronah289/news-portal.git`
to clone to your machine.
* `$ cd news-portal`
* `$ psql < create.sql`
* Open the application in a java-supporting IDE...

## Behaviour Driven Development
#### News-Portal
<ol>
<h1>Method: POST</h1>
<li>Create a new user</li>
- users/new (name,description,staff_role)
<li>Create a new department</li>
- /departments/new (name,description)
<li>Create a new general news</li>
- /news/new/general (title,description,user_id)
<li>Create a new departmental news</li>
- /news/new/department (title,description,department_id,user_id)
<li>Add user to department</li>
- /add/user/:user_id/department/:department_id (user_id,department_id)
<h1>Method: GET</h1>
<li>view users</li>
- /users
<li>View departments</li>
- /departments
<li>View general news</li>
- /news/general
<li>View department of a user</li>
- /user/user_id/departments
<li>View details of certain user</li>
- /user/id
<li>View users of a certain department</li>
- /department/department_id/users
<li>View news of certain department</li>
- /news/department/department_id
</ol>

## Technologies Used

* Java (version 11)
* Gradle (7.1)
* Spark
* Heroku


## License Information

* This software is licensed under MIT License.
* [Read More](https://choosealicense.com/licenses/mit/) on the license.