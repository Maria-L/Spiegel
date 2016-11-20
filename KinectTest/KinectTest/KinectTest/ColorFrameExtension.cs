using Microsoft.Kinect;
using System.Runtime.InteropServices;
using System.Windows;
using System.Windows.Media;
using System.Windows.Media.Imaging;

namespace KinectTest
{

    public static class ColorFrameExtension
    {

        public static WriteableBitmap ToBitmap(this ColorFrame frame)
        {
            int width = frame.FrameDescription.Width;
            int height = frame.FrameDescription.Height;
            byte[] pixels = new byte[width * height * 4];
            WriteableBitmap bitmap = new WriteableBitmap(width, height, 96.0, 96.0, PixelFormats.Bgr32, null);

            if (frame.RawColorImageFormat == ColorImageFormat.Bgra)
            {
                frame.CopyRawFrameDataToArray(pixels);
            }
            else
            {
                frame.CopyConvertedFrameDataToArray(pixels, ColorImageFormat.Bgra);
            }

            bitmap.Lock();

            Marshal.Copy(pixels, 0, bitmap.BackBuffer, pixels.Length);
            bitmap.AddDirtyRect(new Int32Rect(0, 0, width, height));

            bitmap.Unlock();

            return bitmap;
        }

        public static WriteableBitmap ToSubBitmap(this ColorFrame frame, int x, int y, int width, int height)
        {
            int originalWidth = frame.FrameDescription.Width;
            int originalHeight = frame.FrameDescription.Height;
            byte[] originalPixels = new byte[originalWidth * originalHeight * 4];
            byte[] subPixels = new byte[width * height * 4];
            WriteableBitmap subBitmap = new WriteableBitmap(width, height, 96.0, 96.0, PixelFormats.Bgr32, null);


            if (frame.RawColorImageFormat == ColorImageFormat.Bgra)
            {
                frame.CopyRawFrameDataToArray(originalPixels);
            }
            else
            {
                frame.CopyConvertedFrameDataToArray(originalPixels, ColorImageFormat.Bgra);
            }

            for (int row = 0; row < originalHeight; row++)
            {
                for (int column = 0; column < originalWidth; column++)
                {
                    if (row >= y && row < y + height && column >= x && column < x + width)
                    {
                        subPixels[((row - y)*width*4) + ((column - x)*4)] = originalPixels[(row*originalWidth*4) + (column*4)];
                        subPixels[((row - y)*width*4) + ((column - x)*4) + 1] = originalPixels[(row*originalWidth*4) + (column*4) + 1];
                        subPixels[((row - y)*width*4) + ((column - x)*4) + 2] = originalPixels[(row*originalWidth*4) + (column*4) + 2];
                        subPixels[((row - y)*width*4) + ((column - x)*4) + 3] = originalPixels[(row*originalWidth*4) + (column*4) + 3];
                    }
                }
            }

            subBitmap.Lock();

            Marshal.Copy(subPixels, 0, subBitmap.BackBuffer, subPixels.Length);
            subBitmap.AddDirtyRect(new Int32Rect(0, 0, width, height));

            subBitmap.Unlock();

            return subBitmap;
        }
    }
}