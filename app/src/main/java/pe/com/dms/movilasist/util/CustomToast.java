package pe.com.dms.movilasist.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.widget.Toast;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.ToastCustomBinding;

public class CustomToast extends Toast {
    private int color;
    private int durationToast;
    private Drawable icon;

    private ToastCustomBinding binding;

    public CustomToast(LayoutInflater layoutActivity, String mensaje, int ToastLength) {
        super(layoutActivity.getContext());
        binding = ToastCustomBinding.inflate(layoutActivity);

        binding.toastMessage.setText(mensaje);
        durationToast = ToastLength;
    }

    @Override
    public void show() {
        this.setView(binding.getRoot());
        this.setDuration(durationToast);
        ((GradientDrawable) binding.toastLayout.getBackground()).setColor(color);
        binding.toastImage.setImageDrawable(icon);
        super.show();
    }

    public static class Builder {
        private LayoutInflater layout;
        private String mensaje;
        private int backgroundColor;
        private int duration;
        private Drawable icon;
        private CustomToast dialog;

        public Builder(Context context, LayoutInflater layout, String mensaje) {
            this.layout = layout;
            this.mensaje = mensaje; //Maximo 2 lineas 64 caracteres aprox
            this.icon = context.getResources().getDrawable(R.drawable.ic_warning); // R.drawable.ic_warning;
            this.backgroundColor = context.getResources().getColor(R.color.colorWarning);
            this.duration = Toast.LENGTH_SHORT;
        }

        public CustomToast build() {
            dialog = new CustomToast(layout, mensaje, duration);
            dialog.color = backgroundColor;
            dialog.icon = icon;
            return dialog;
        }

        public Builder setBackgroundColor(int color) {
            this.backgroundColor = color;
            return this;
        }

        public Builder setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setMessage(String message) {
            this.mensaje = message;
            return this;
        }
    }
}
