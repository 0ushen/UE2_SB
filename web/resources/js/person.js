$(document).ready(function () {
   
   console.log("Hello");
   
   function toggleDetailsBox() {
       
       console.log('click');
       if($('#details-box').html() === ''){
            /* Results box is cut in half in order to show the detail box 
             * next to it on big screens */

            $('#results-box').toggleClass('col-xl-12 col-xl-6');

            /* Load the details from the row the user clicked on in a form .
             * Data can be changed directly in the database via this 
             * interface. */

            $('#details-box').load('person_details.xhtml', function(){

                
            });
        }
        else{
            /* If the user clicked on the table while there is a detail box,
             * the detail box html content is emptied and the table takes all
             * the width possible on large screens */
            $('#details-box').html('');
            $('#results-box').toggleClass('col-xl-12 col-xl-6');
        }
   }
   
   
});