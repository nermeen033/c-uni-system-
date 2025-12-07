#ifndef RAFAA_H
#define RAFAA_H

#include <QMainWindow>

QT_BEGIN_NAMESPACE
namespace Ui {
class rafaa;
}
QT_END_NAMESPACE

class rafaa : public QMainWindow
{
    Q_OBJECT

public:
    rafaa(QWidget *parent = nullptr);
    ~rafaa();

private:
    Ui::rafaa *ui;
};
#endif // RAFAA_H
