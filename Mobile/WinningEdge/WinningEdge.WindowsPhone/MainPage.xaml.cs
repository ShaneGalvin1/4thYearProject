using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using Microsoft.WindowsAzure.MobileServices;
using Newtonsoft.Json;
using System.Collections.ObjectModel;

// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkId=391641

namespace WinningEdge
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    
    public class Test
    {
        public int Id { get; set; }

        [JsonProperty(PropertyName = "s")] 
        public String s { get; set; }

        [JsonProperty(PropertyName = "complete")] 
        public bool Complete { get; set; }
        
    }
    public sealed partial class MainPage : Page
    {
        // Use this constructor instead after publishing to the cloud
       // public static MobileServiceClient MobileService = new MobileServiceClient(
         //    "https://winningedge.azure-mobile.net/",
           //  "WDXoKJaqMiTMDfEgmQFclxtqLLvtEP94"
       //);

        // TODO: Comment out the following line that defined the in-memory collection. 
       // private ObservableCollection<Test> items = new ObservableCollection<Test>();

        //// TODO: Uncomment the following lines that defines the Mobile Servies table.
        //// TODO: Replace yourClient with the MobileServiceClient instance added to 
        //// the App.xaml.cs file when connecting to your service.
        private MobileServiceCollection<Test, Test> items;
        private IMobileServiceTable<Test> testTable =
            App.MobileService.GetTable<Test>();
        
        public MainPage()
        {
            this.InitializeComponent();

            this.NavigationCacheMode = NavigationCacheMode.Required;
        }

        private async void InsertTest(Test test)
        {
            // TODO: Delete or comment the following statement; Mobile Services auto-generates the ID. 
            //       You can also leave this line if you want to generate your own unique id values.
            //todoItem.Id = Guid.NewGuid().ToString();

            //// This code inserts a new TodoItem into the database. When the operation completes 
            //// and Mobile Services has assigned an Id, the item is added to the CollectionView 
            //// TODO: Mark this method as "async" and uncomment the following statement. 
            await testTable.InsertAsync(test); 

            items.Add(test);
        }

        private void uploadButton_Click(object sender, RoutedEventArgs e)
        {
            var message = this.Input.Text;
            if(String.IsNullOrEmpty(message))
            {
                this.Input.Text = "Field Required";
            }
            else
            {
                Test t = new Test();
                t.s = message;
                InsertTest(t);
                this.Frame.Navigate(typeof(BlankPage1), message);
            }
            
        }

        /// <summary>
        /// Invoked when this page is about to be displayed in a Frame.
        /// </summary>
        /// <param name="e">Event data that describes how this page was reached.
        /// This parameter is typically used to configure the page.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            // TODO: Prepare page for display here.
            
            
            // TODO: If your application contains multiple pages, ensure that you are
            // handling the hardware Back button by registering for the
            // Windows.Phone.UI.Input.HardwareButtons.BackPressed event.
            // If you are using the NavigationHelper provided by some templates,
            // this event is handled for you.
        }
    }
}
