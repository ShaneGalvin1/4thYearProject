
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Storage.Pickers.Provider;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using Microsoft.WindowsAzure.MobileServices;
using Newtonsoft.Json;
using WinningEdgeService.Models;


// The Content Dialog item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace WinningEdge
{
    public sealed partial class Register : ContentDialog
    {
        private MobileServiceCollection<User, User> users;
        private IMobileServiceTable<User> userTable =
           App.MobileService.GetTable<User>();
        Frame rootFrame = Window.Current.Content as Frame; 
        private String mEmail;
        private String mPassword;
        private User mUser;

        public Register()
        {
            this.InitializeComponent();
        }
        

        //public static MobileServiceClient MobileService = new MobileServiceClient(
        //  "http://localhost:50512"
        //);
        // Use this constructor instead after publishing to the cloud
        public static MobileServiceClient MobileService = new MobileServiceClient(
             "https://winningedge.azure-mobile.net/",
             "WDXoKJaqMiTMDfEgmQFclxtqLLvtEP94"
       );




        private async void RegisterDialog_PrimaryButtonClick(ContentDialog sender, ContentDialogButtonClickEventArgs args)
        {
            mEmail = rEmail.Text;
            mPassword = rPassword.Password;
            mUser = new User(mEmail, mPassword);
            await userTable.InsertAsync(mUser);
            rootFrame.Navigate(typeof(Register));
        }

    }
}
