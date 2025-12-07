#include "rafaa.h"
#include "./ui_rafaa.h"

rafaa::rafaa(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::rafaa)
{
    ui->setupUi(this);
}

rafaa::~rafaa()
{
    delete ui;
}
