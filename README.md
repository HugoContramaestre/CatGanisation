# Cat App
## Description
This app shows a list of cats from TheCatApi's public API. To access the app the user must be logged, 
so there is a login fragment with a mocked login service but with active validation checks on the 
front. After accessing the app the user goes to a fragment that shows a list of cat breeds, 
by clicking on any of the items, a fragment, showing the details of that item opens, allowing the 
user to see more info about that item (image, name of the cat breed, description, country's code, 
the breed's temperament and an url to the it's wikipedia article).
The application is shown using a single Activity, and the navigation through different fragments is 
achieved by using a single flow of fragments.

The project has been modularized according to these modules: app, data, domain, useCases and 
testShared.

In the project there are some abstract classes that serve as the base classes for the activities, 
fragments, adapters and viewModels that the project may need. Inside these classes there are 
basic attributes and methods that their children may use or override.

For the possible communication between the fragments and the activity there is a viewModel called
SharedViewModel, which has defined LiveData's and methods that allow the possibility to communicate 
to the activity from a fragment, so the app can show a SnackBar showing an error massage due to a 
server error or to show a loader that locks the screen while the data is loading.

For navigating through the app the Navigation Component was implemented by creating a single flow of 
fragments that goes from the login to the detail's screen, these fragments are shown using a 
FragmentContainerView defined in the MainActivity.

Each Fragment has assigned a viewModel, for this app the functionality of the viewModels are limited
to calling the invoke() method from the respective useCases to obtain the info that the fragment 
requires from the server. In order to make these requests to the server the kotlin coroutines are 
used to received asynchronously the server's response, this response comes inside an Either object
that can harbour inside it either the expected object from the response or the failure object that 
tells the type of error that has occurred during the request.

The LoginFragment is a simple layout with two TextInputEditText to write the username and the 
password and a button that, when clicked, calls the ViewModel to send the information to the server 
in order to log in the app, since there is no login service for this app, the functionality of this 
feature has been mocked to a certain point: it validates that the fields content are valid before 
calling the use case and after that the login request is made, the request will return a mocked 
object of the domain response called "LoginResponse". 

In case any of the fields is not valid, the invalid field's TextInputLayout is marked as an error 
with a message telling the user what's need to be written to be valid. For these fields the next 
validations must be checked: the username must be between 3 to 16 alphanumeric characters long and 
for the password there is a minimum of 5 characters.

The fragment BreedsListFragment have defined in it's layout: a recyclerView to
show the list, and empty view to show if there is no content and a retry button that is hidden until
the request for the list's content fails. the recyclerView implements an adapter that extends 
BaseListAdapter and implements a skeletonView that works as a placeholder that shows the visual 
structure of the adapter's items while the user awaits for the server's response. this skeleton can
be implement by hand, defining the skeleton's viewHolder inside the adapter or by calling an 
extension method in SkeletonExtensions, although that method is not being used I decided to leave it
just to show that there is also that possibility. 
The skeleton, the empty screen, the list and the retry button will be shown according to the value 
of the list's state, defined in the sealed class ListState. These possible values are: Loading, 
Error, Empty and Done.

The fragment BreedDetailFragment show's a big image of the item, the name
of the cat breed, the breed's description, the breed's country code, the breed's temperament of and 
an url for that breed's wikipedia article if there is one.

The service's calls are defined using Retrofit in ApiServices, for the response, the object 
NetworkResponse is used to manage the response by receiving the response object in case it's a 
success or the error object if there has been an error in the request. To call to any of the methods 
in ApiServices the methods inside ApiRemoteDataSource are called, here we can define: how the object 
from the service will be mapped from the response object to the domain object, and how the error 
object from the response will be transformed into a Failure object, these objects are the possible 
errors from the server that can happen and the ones that will be used on the front part of the app 
to show the error message through the SnackBar.

The project uses Koin as DI because it's easier and more comfortable to use and implement, the 
dependencies for the DI are defined in the di.kt file.

## Features
* Modular architecture based on the CLEAN architecture layer separation.
* MVVM
* Coroutines
* Koin
* Navigation components
* Data binding
* Use of a skeleton library to show the placeholders of the list's items while the user is waiting
for the server's response