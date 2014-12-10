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

// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace WinningEdge
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class BlankPage1 : Page
    {
        // Use this constructor instead after publishing to the cloud
        //public static MobileServiceClient MobileService = new MobileServiceClient(
          //   "https://winningedge.azure-mobile.net/",
            // "WDXoKJaqMiTMDfEgmQFclxtqLLvtEP94"
       //);
        private MobileServiceCollection<Test, Test> items;
        private IMobileServiceTable<Test> testTable =
            App.MobileService.GetTable<Test>();
        public BlankPage1()
        {
            this.InitializeComponent();
        }

        private async void RefreshTestItems()
        {
            //// TODO #1: Mark this method as "async" and uncomment the following statment 
            //// that defines a simple query for all items.  
            items = await testTable.ToCollectionAsync(); 

            //// TODO #2: More advanced query that filters out completed items.  
            items = await testTable 
               .Where(testItem => testItem.Complete == false) 
               .ToCollectionAsync();

            this.DownloadBox.Text = items.First().s;
            //ListItems.ItemsSource = items;
        }

        private void downloadButton_Click(object sender, RoutedEventArgs e)
        {
            RefreshTestItems();
        }

        /// <summary>
        /// Invoked when this page is about to be displayed in a Frame.
        /// </summary>
        /// <param name="e">Event data that describes how this page was reached.
        /// This parameter is typically used to configure the page.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
        }
    }
}
