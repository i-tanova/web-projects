//console.log(firebase)

const auth = firebase.auth();

// Take the elements from the HTML
const whenSignedInSection = document.getElementById('whenSignedIn');
const whenSignedOutSection = document.getElementById('whenSignedOut');

const signOutBtn = document.getElementById('signOutBtn');
const signInBtn = document.getElementById('signInBtn');

const userDetails = document.getElementById('userDetails');

// This is Sign in with Google provider, for other sign in methods you need other providers
const provider = new firebase.auth.GoogleAuthProvider();

// Attach on click listeners
signInBtn.onclick = () => auth.signInWithPopup(provider);

signOutBtn.onclick = () => auth.signOut();

//Listen to changes in the authentication
auth.onAuthStateChanged(user => {
    if (user) {
        //signed in
        whenSignedOutSection.hidden = true
        whenSignedInSection.hidden = false
        userDetails.innerHTML = `<h3>Hello ${user.displayName}!</h3>`

    } else {
        //signed out
        whenSignedOutSection.hidden = false
        whenSignedInSection.hidden = true
        userDetails.innerHTML = ``
    }
});

// Start here with Firestore
const db = firebase.firestore();

const list = document.getElementById('thingsList');
const createBtn = document.getElementById('createThing');

let productsRef; // Reference to DB location
let unsubscribe;
////////////
//Listen to changes in the authentication
auth.onAuthStateChanged(user => {
    if (user) {
        //User is signed in
        productsRef = db.collection('userdata')
         ///////////////////
        // //Get products list
        //  productsRef.where('uid', '==', user.uid).get().then(function(docs) {
        //     if (docs.exists) {
        //         console.log("Document data:", docs.data());
        //         const items = docs.map(
        //             doc => {
        //                 return `<li>${doc.data().date}</li>`
        //             }
        //         );
        //         list.innerHTML = items.join('');
    
        //     } else {
        //         // doc.data() will be undefined in this case
        //         console.log("No such document!");
        //     }
        // }).catch(function(error) {
        //     console.log("Error getting document:", error);
        // });

        //////////////////////
         // Subscribe to listen for products changes
         unsubscribe = productsRef
         .where('uid', '==', user.uid) // Subscribe only for our current user
         .onSnapshot(querySnapshot => {
             //runs when data changes
             const items = querySnapshot.docs.map(
                 doc => {
                     return `<li>${doc.data().date}</li>`
                 }
             );
             list.innerHTML = items.join('');
         }); // get() will give us one shot data


         //////////////////////
         // Listen for create products
        createBtn.onclick = () => {

            // Correct way to put data with FB!!
            const { serverTimestamp } = firebase.firestore.FieldValue;

            //create new document
            // reference user with the document
            productsRef.add({
                uid: user.uid,
                productId: 1,
                date: serverTimestamp() // Correct way to put data with FB!!
            });
        }

    } else {
        // Unsubscribe to listening for products
        unsubscribe && unsubscribe();
    }
});

