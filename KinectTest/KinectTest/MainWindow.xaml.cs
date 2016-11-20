using System.Collections.Generic;
using System.Windows;
using Microsoft.Kinect;



//Diagramme Tutorial https://msdn.microsoft.com/de-de/library/dd489237.aspx
// Interessante Doku https://www.uni-trier.de/fileadmin/urt/doku/csharp/v40/csharp4.pfd
namespace KinectTest
{

    public partial class MainWindow : Window
    {
        private KinectSensor sensor = null;
        private ColorFrameReader colorFrameReader;
        //private BodyFrameReader bodyFrameReader;
       // private IList<Body> bodies;
       // private IDictionary<ulong, CameraSpacePoint> headPositions = new Dictionary<ulong, CameraSpacePoint>();

        public MainWindow()
        {
            InitializeComponent();

            sensor = KinectSensor.GetDefault();
            //browser.Source("C:\\Users\\CSTI\\Documents\\Spiegel1\\KinectTest\\mirror\\index.html");
            
            if (sensor != null)
            {
                sensor.Open();

              //  bodies = new Body[sensor.BodyFrameSource.BodyCount];

                colorFrameReader = sensor.ColorFrameSource.OpenReader();
                colorFrameReader.FrameArrived += OnColorFrameArrived;
                //bodyFrameReader = sensor.BodyFrameSource.OpenReader();
                //bodyFrameReader.FrameArrived += OnBodyFrameArrived;
            }
        }

        private void OnColorFrameArrived(object sender, ColorFrameArrivedEventArgs args)
        {
            using (var frame = args.FrameReference.AcquireFrame())
            {
                if (frame != null)
                {

                    // foreach (KeyValuePair<ulong, CameraSpacePoint> headPosition in headPositions)
                    // {
                    // ColorSpacePoint position = sensor.CoordinateMapper.MapCameraPointToColorSpace(headPosition.Value);
                    //camera.Source = frame.ToSubBitmap((int)position.X - 50, (int)position.Y - 50, 100, 100);
           
                    //camera.Source = frame.ToBitmap();
                  //Hier wird irgendwas nicht gefunden. Was ist das Problem mitder Viewbox und dem camera Objekt?
                    camera.Source = frame.ToSubBitmap(270, 0, 640, 1080);                  
                    // }

                    frame.Dispose();
                }
            }
        }

        //private void OnBodyFrameArrived(object sender, BodyFrameArrivedEventArgs args)
        //{
        //    using (var frame = args.FrameReference.AcquireFrame())
        //    {
        //        if (frame != null)
        //        {
        //            frame.GetAndRefreshBodyData(bodies);
        //            headPositions.Clear();

        //            foreach (var body in bodies)
        //            {
        //                if (body.IsTracked)
        //                {
        //                    headPositions[body.TrackingId] = body.Joints[JointType.Head].Position;
        //                }   
        //            }


        //        }
        //    }
        //}
    }
}