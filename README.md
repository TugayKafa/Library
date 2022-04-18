
# 🚀<img alt="ANDROMEDA" width="40" height="25" src="https://user-images.githubusercontent.com/17432777/160013133-edce2b85-bd19-4f7c-a74b-65d77dfffd20.png"/>Andromeda Library

The project represents Library of books. It contains `User-API`, `Library-API` and `Asssignment-API`.

* `User-API includes all users of our library. There are two types of users - Active and Inactive.`

* `Library-API includes all books in library. There are two types of books - Active and Inactive.`

* `Assignment-API cares to assign active books to active users. If there have some user who did not return his books on time, the user will have the option to extend his period for return the books or the user will be sanctioned from library.`
## 🗹Prerequisites

Before you begin, ensure you have met the following requirements:
* <a href="https://www.jetbrains.com/idea/">IntelliJ IDEA</a> / <a href="https://www.eclipse.org/downloads/">Eclipse</a>
* <a href="https://www.oracle.com/java/technologies/downloads/#java11">JDK 11 or newer</a>
* <a href="https://firebase.google.com/">Firebase storage</a>
##  🛠️Installing Andromeda Library

To install Andromeda-Library, follow these steps:

Clone this git repository
```
https://github.com/endava-school-of-java-2022/andromeda-library
```

To run User-API or Library-API:
1. Open IntelliJ 
3. Click `Open`
4. Find the directory where you saved repository and select API that you want to run
5. Select the `pom.xml` file for this API
<img src="https://user-images.githubusercontent.com/86955459/159993677-b7cb0a85-e66f-4d39-9c89-63cbcfbfe966.png" height = "500" alt=""/>

7. Click `Open as Project`
8. Click `Trust Project`
9. Fill `application.properties` file
10. Before start the Library-API you must execute the script in `book-api/src/main/resources/sql/genres.sql` to fill books with genres.
11. Run the project

To run Assignment-API:
1. Run User-API
2. File/Project Structure
3. Modules
4. Add Library-API
<img src="https://user-images.githubusercontent.com/86955459/160006496-c6bf00a4-aa8c-4207-97ae-72e074f5079d.png" height = "500" alt=""/>

6. Run LIbrary-APi
7. Add Assignment-API
8. Fill `application.properties` file
9. Run Assignment-API

You have to run different APIs in different ports!

In aplication.properties you have to add.

```
server.port=<PORT>
```
The default port is 8080.

##  👨‍💻Contributors

Thanks to the following people who have contributed to this project:

<table>
  <tr>
    <td align="center"><a href="https://github.com/BojidarVn"><img src="https://avatars.githubusercontent.com/u/73103322?v=4" width="100px;" alt=""/><br /><sub><b>Bozhidar Baltadzhiev</b></sub></a><br /></a><a href="https://bg.linkedin.com/in/bojidar-baltadjiev-515543162" title="LinkedIn Profile">👨‍💼</a></td>
    <td align="center"><a href="https://github.com/ivtanev"><img src="https://avatars.githubusercontent.com/u/22391738?v=4" width="100px;" alt=""/><br /><sub><b>Ivan Tanev</b></sub></a><br /><a href="https://github.com/endava-school-of-java-2022/andromeda-library/commits/dev?author=ivtanev" title="Documentation">📖</a> <a href="https://github.com/endava-school-of-java-2022/andromeda-library/pulls?q=is%3Apr+is%3Aclosed+author%3Aivtanev" title="Reviewed Pull Requests">👀</a><a href="https://bg.linkedin.com/in/ivan-tanev-5061bb166/" title="LinkedIn Profile">👨‍💼</a></td>
    <td align="center"><a href="https://github.com/iliyandzh"><img src="https://avatars.githubusercontent.com/u/43420012?v=4" width="100px;" alt=""/><br /><sub><b>Iliyan Dzhondzhov</b></sub></a><br /><a href="https://github.com/endava-school-of-java-2022/andromeda-library/commits/dev?author=iliyandzh" title="Documentation">📖</a> <a href="https://github.com/endava-school-of-java-2022/andromeda-library/pulls?q=is%3Apr+is%3Aclosed+author%3Ailiyandzh" title="Reviewed Pull Requests">👀</a><a href="https://bg.linkedin.com/in/iliyan-dzhondzhov-6b8485178" title="LinkedIn Profile">👨‍💼</a></td>
    <td align="center"><a href="https://github.com/WalkZa"><img src="https://avatars.githubusercontent.com/u/17432777?v=4" width="100px;" alt=""/><br /><sub><b>Simeon Nikolov</b></sub></a><br /><a href="https://github.com/endava-school-of-java-2022/andromeda-library/commits/dev?author=WalkZa" title="Documentation">📖</a> <a href="https://github.com/endava-school-of-java-2022/andromeda-library/pulls?q=is%3Apr+is%3Aclosed+author%3AWalkZa" title="Reviewed Pull Requests">👀</a><a href="https://bg.linkedin.com/in/simeonnikolov" title="LinkedIn Profile">👨‍💼</a></td>
    <td align="center"><a href="https://github.com/TugayKafa"><img src="https://avatars.githubusercontent.com/u/86955459?v=4" width="100px;" alt=""/><br /><sub><b>TugayKafa</b></sub></a><br /><a href="https://github.com/endava-school-of-java-2022/andromeda-library/commits/dev?author=TugayKafa" title="Documentation">📖</a> <a href="https://github.com/endava-school-of-java-2022/andromeda-library/pulls?q=is%3Apr+is%3Aclosed+author%3ATugayKafa" title="Reviewed Pull Requests">👀</a><a href="https://bg.linkedin.com/in/tugaykafa" title="LinkedIn Profile">👨‍💼</a></td>
  </tr>
</table>

## 🙏Special Thanks For

<table>
  <tr>
    <td align="center"><a href="https://github.com/aleksandar-rusev-endava"><img src="https://avatars.githubusercontent.com/u/80454459?v=4" width="100px;" alt=""/><br /><sub><b>Alexander Rusev</b></sub></a><br/></td>
<td align="center"><a href="https://github.com/silvia-ivanova"><img src="https://avatars.githubusercontent.com/u/97459392?v=4" width="100px;" alt=""/><br /><sub><b>Silvia Ivanova</b></sub></a><br/></td>
<td align="center"><a href="https://github.com/school-of-java-admin"><img src="https://avatars.githubusercontent.com/u/80211692?v=4" width="100px;" alt=""/><br /><sub><b>Todor Radev</b></sub></a><br/></td>
</table>

## 💰Sponsor 
<a href="https://www.endava.com/"><img src="https://www.endava.com/-/media/EndavaDigital/Endava/Images/MetaDataImages/preview-image.ashx" height = "100" alt=""/></a>

